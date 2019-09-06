package com.techdevsolutions.shared.dao.yahoo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techdevsolutions.shared.beans.yahoo.Quote;
import com.techdevsolutions.shared.service.DateUtils;
import com.techdevsolutions.shared.service.HashUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class YahooFinanceDAO {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    protected ObjectMapper objectMapper = new ObjectMapper();

    public Quote get(String symbol) throws Exception {
        if (StringUtils.isEmpty(symbol) || StringUtils.isEmpty(symbol.trim())) {
            throw new IllegalArgumentException("symbol is null or empty");
        }

        String symbolUpperCased = symbol.toUpperCase();

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://query1.finance.yahoo.com/v8/finance/chart/" + symbolUpperCased + "?" +
                "interval=1d");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HashMap<String,Object> contentAsMap = null;

        Thread.sleep(500);

        try {
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            logger.debug(content);
            contentAsMap = this.objectMapper.readValue(content, new TypeReference<HashMap<String, Object>>() {
            });
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.close();
        }

        if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300) {
            HashMap<String,Object> chart = (HashMap<String,Object>) contentAsMap.get("chart");
            List<HashMap<String,Object>> results = (List<HashMap<String,Object>>) chart.get("result");
            HashMap<String,Object> result = results.get(0);
            HashMap<String,Object> indicators = (HashMap<String,Object>) result.get("indicators");
            List<HashMap<String,Object>> quotes = (List<HashMap<String,Object>>) indicators.get("quote");
            HashMap<String,Object> quote = quotes.get(0);
            List<Double> opens = (List<Double>) quote.get("open");
            List<Double> closes = (List<Double>) quote.get("close");
            List<Double> high = (List<Double>) quote.get("high");
            List<Double> low = (List<Double>) quote.get("low");
            List<Integer> volume = (List<Integer>) quote.get("volume");

            List<Integer> timestamp = (List<Integer>) result.get("timestamp");
            Integer time = timestamp.get(timestamp.size() - 1);
            Date date = new Date(time * 1000L);

            SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.YYYY_MM_DD_STRING);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

            Quote i = new Quote();
            i.setSymbol(symbolUpperCased);
            i.setOpen(opens.get(0));
            i.setClose(closes.get(0));
            i.setHigh(high.get(0));
            i.setLow(low.get(0));
            i.setVolume(volume.get(0));
            i.setDate(date);
            i.setName(symbolUpperCased + " " + sdf.format(date));
            i.setCreatedBy("?");
            i.setCreatedDate(new Date());
            i.setUpdatedBy(i.getCreatedBy());
            i.setUpdatedDate(i.getCreatedDate());
            i.setId(symbolUpperCased + "-" + sdf.format(date));
            return i;
        } else {
            HashMap<String,Object> chart = (HashMap<String,Object>) contentAsMap.get("chart");
            HashMap<String,Object> error = (HashMap<String,Object>) chart.get("error");
            String description = (String) error.get("description");
            throw new Exception("Unable to get quote for: " + symbolUpperCased + ". Reason: " + description);
        }
    }

    public List<Quote> get(String symbol, Date from, Date to) throws Exception {
        if (StringUtils.isEmpty(symbol) || StringUtils.isEmpty(symbol.trim())) {
            throw new IllegalArgumentException("symbol is null or empty");
        } else if (from == null) {
            throw new IllegalArgumentException("from is null or empty");
        } else if (to == null) {
            throw new IllegalArgumentException("from is null or empty");
        }

        String symbolUpperCased = symbol.toUpperCase();
        Long toSeconds = to.getTime() / 1000;
        Long fromSeconds = from.getTime() / 1000;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://query1.finance.yahoo.com/v8/finance/chart/" + symbol + "?" +
                "interval=1d&" +
                "period1=" + fromSeconds + "&" +
                "period2=" + toSeconds + "&" +
                "lang=en-US&" +
                "region=US");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HashMap<String,Object> contentAsMap = null;

        Thread.sleep(500);

        try {
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            logger.debug(content);
            contentAsMap = this.objectMapper.readValue(content, new TypeReference<HashMap<String, Object>>() {
            });
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.close();
        }

        if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300) {
            HashMap<String,Object> chart = (HashMap<String,Object>) contentAsMap.get("chart");
            List<HashMap<String,Object>> results = (List<HashMap<String,Object>>) chart.get("result");
            HashMap<String,Object> result = results.get(0);
            HashMap<String,Object> indicators = (HashMap<String,Object>) result.get("indicators");
            List<HashMap<String,Object>> quotes = (List<HashMap<String,Object>>) indicators.get("quote");
            HashMap<String,Object> quote = quotes.get(0);
            List<Double> opens = (List<Double>) quote.get("open");
            List<Double> closes = (List<Double>) quote.get("close");
            List<Double> high = (List<Double>) quote.get("high");
            List<Double> low = (List<Double>) quote.get("low");
            List<Integer> volume = (List<Integer>) quote.get("volume");
            List<Integer> timestamp = (List<Integer>) result.get("timestamp");

            SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.YYYY_MM_DD_STRING);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            List<Integer> counter = new ArrayList<>();

            List<Quote> data = opens.stream().map((open)->{
                Integer c = counter.size();

                Quote i = new Quote();
                i.setSymbol(symbolUpperCased);
                i.setOpen(opens.get(c));
                i.setClose(closes.get(c));
                i.setHigh(high.get(c));
                i.setLow(low.get(c));
                i.setVolume(volume.get(c));
                Integer time = timestamp.get(c);
                Date date = new Date(time * 1000L);
                i.setDate(date);
                i.setName(symbolUpperCased + " " + sdf.format(date));
                i.setCreatedBy("?");
                i.setCreatedDate(new Date());
                i.setUpdatedBy(i.getCreatedBy());
                i.setUpdatedDate(i.getCreatedDate());
                i.setId(symbolUpperCased + "-" + sdf.format(date));

                counter.add(1);
                return i;
            }).collect(Collectors.toList());

            return data;
        } else {
            HashMap<String,Object> chart = (HashMap<String,Object>) contentAsMap.get("chart");
            HashMap<String,Object> error = (HashMap<String,Object>) chart.get("error");
            String description = (String) error.get("description");
            throw new Exception("Unable to get quote for: " + symbolUpperCased + ". Reason: " + description);
        }
    }
}

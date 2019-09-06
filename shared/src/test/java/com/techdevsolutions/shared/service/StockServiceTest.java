package com.techdevsolutions.shared.service;

import com.techdevsolutions.shared.beans.yahoo.Quote;
import com.techdevsolutions.shared.dao.elasticsearch.QuoteElasticsearchDAO;
import com.techdevsolutions.shared.dao.yahoo.YahooFinanceDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StockServiceTest {
    YahooFinanceDAO yahooFinanceDAO = new YahooFinanceDAO();
    QuoteElasticsearchDAO quoteElasticsearchDAO = new QuoteElasticsearchDAO();
    StockService stockService = new StockService(this.yahooFinanceDAO, this.quoteElasticsearchDAO);

    @Before
    public void init() {
    }

    @Ignore
    @Test
    public void get() throws Exception {
        Quote quote = this.stockService.acquire("spy");
        Assert.assertTrue(quote != null);
        this.stockService.save(quote);
        Thread.sleep(5000L);
    }

    @Ignore
    @Test
    public void getAYear() throws Exception {
        Date to = new Date();
        Date from = new Date(to.getTime() - (1000L * 60 * 60 * 24 * 365));
        List<Quote> quotes = this.stockService.acquire("spy", from, to);
        Assert.assertTrue(quotes != null);
        this.stockService.save(quotes);
        Thread.sleep(5000L);
    }

    @Ignore
    @Test
    public void getMultiple() throws Exception {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        Date to = new Date();
        Date from = new Date(to.getTime() - (1000L * 60 * 60 * 24 * 365));
//        List<String> symbols = StockService.SP_100;
        List<String> symbols = new ArrayList<>(Arrays.asList("aapl"));

                symbols.forEach((symbol)->{
            try {
                List<Quote> quotes = this.stockService.acquire(symbol, from, to);
                this.stockService.save(quotes);
                Thread.sleep(500L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread.sleep(5000L);
    }

    @Ignore
    @Test
    public void save() throws Exception {
        Quote quote = this.stockService.get("aapl");
        this.stockService.save(quote);
    }

    @Ignore
    @Test
    public void integration() throws Exception {
        Quote quote = this.stockService.acquire("aapl");
        quote = this.stockService.saveAndReturn(quote);
        this.stockService.delete(quote);
    }
}

package com.techdevsolutions.shared.service;

import com.techdevsolutions.shared.beans.yahoo.Quote;
import com.techdevsolutions.shared.dao.elasticsearch.QuoteElasticsearchDAO;
import com.techdevsolutions.shared.dao.yahoo.YahooFinanceDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    protected YahooFinanceDAO yahooFinanceDAO;
    protected Integer DELAY_MILLI = 250;
    protected QuoteElasticsearchDAO quoteElasticsearchDAO;

    // As of 9/3/19
    public static List<String> SP_100 = new ArrayList<>(Arrays.asList(
            "AAPL",
            "ABBV",
            "ABT",
            "ACN",
            "ADBE",
            "AGN",
            "AIG",
            "ALL",
            "AMGN",
            "AMZN",
            "AXP",
            "BA",
            "BAC",
            "BIIB",
            "BK",
            "BKNG",
            "BLK",
            "BMY",
            "BRK.B",
            "C",
            "CAT",
            "CELG",
            "CHTR",
            "CL",
            "CMCSA",
            "COF",
            "COP",
            "COST",
            "CSCO",
            "CVS",
            "CVX",
            "DD",
            "DHR",
            "DIS",
            "DOW",
            "DUK",
            "EMR",
            "EXC",
            "F",
            "FB",
            "FDX",
            "GD",
            "GE",
            "GILD",
            "GM",
            "GOOG",
            "GOOGL",
            "GS",
            "HD",
            "HON",
            "IBM",
            "INTC",
            "JNJ",
            "JPM",
            "KHC",
            "KMI",
            "KO",
            "LLY",
            "LMT",
            "LOW",
            "MA",
            "MCD",
            "MDLZ",
            "MDT",
            "MET",
            "MMM",
            "MO",
            "MRK",
            "MS",
            "MSFT",
            "NEE",
            "NFLX",
            "NKE",
            "NVDA",
            "ORCL",
            "OXY",
            "PEP",
            "PFE",
            "PG",
            "PM",
            "PYPL",
            "QCOM",
            "RTN",
            "SBUX",
            "SLB",
            "SO",
            "SPG",
            "T",
            "TGT",
            "TXN",
            "UNH",
            "UNP",
            "UPS",
            "USB",
            "UTX",
            "V",
            "VZ",
            "WBA",
            "WFC",
            "WMT",
            "XOM"));

    @Autowired
    public StockService(YahooFinanceDAO yahooFinanceDAO, QuoteElasticsearchDAO quoteElasticsearchDAO) {
        this.yahooFinanceDAO = yahooFinanceDAO;
        this.quoteElasticsearchDAO = quoteElasticsearchDAO;

        try {
            this.quoteElasticsearchDAO.setup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Quote acquire(String symbol) throws Exception {
        this.logger.debug("Acquiring " + symbol.toUpperCase() + " ...");
        Quote quote = this.yahooFinanceDAO.get(symbol);
        this.logger.debug("Acquiring " + symbol.toUpperCase() + " ... [DONE]");
        return quote;
    }

    public List<Quote> acquire(String symbol, Date from, Date to) throws Exception {
        this.logger.debug("Acquiring " + symbol.toUpperCase() + " from " + from + " to " + to + "...");
        List<Quote> quotes = this.yahooFinanceDAO.get(symbol, from, to);
        this.logger.debug("Acquiring " + symbol.toUpperCase() + "... [DONE]");
        return quotes;
    }

    public List<Quote> acquire(List<String> symbols) {
        return symbols.stream().map((i)->{
            Quote quote = null;

            try {
                quote = this.acquire(i);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(this.DELAY_MILLI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return quote;
        }).collect(Collectors.toList());
    }

    public List<List<Quote>> acquire(List<String> symbols, Date from, Date to) {
        return symbols.stream().map((i)->{
            List<Quote> quotes = null;

            try {
                quotes = this.acquire(i, from, to);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(this.DELAY_MILLI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return quotes;
        }).collect(Collectors.toList());
    }

    public Quote get(String id) throws Exception {
       return this.quoteElasticsearchDAO.get(id);
    }

    public void save(Quote quote) throws Exception {
        this.quoteElasticsearchDAO.create(quote);
    }

    public void save(List<Quote> quotes) throws Exception {
        this.quoteElasticsearchDAO.createBulk(quotes);
    }

    public Quote saveAndReturn(Quote quote) throws Exception {
        this.quoteElasticsearchDAO.create(quote);
        return this.quoteElasticsearchDAO.get(quote.getId());
    }

    public void delete(Quote quote) throws Exception {
        this.quoteElasticsearchDAO.delete(quote);
    }
}

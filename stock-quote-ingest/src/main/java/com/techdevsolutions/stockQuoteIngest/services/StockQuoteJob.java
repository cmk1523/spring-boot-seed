package com.techdevsolutions.stockQuoteIngest.services;

import com.techdevsolutions.shared.beans.yahoo.Quote;
import com.techdevsolutions.shared.service.DateUtils;
import com.techdevsolutions.shared.service.StockService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class StockQuoteJob implements Job {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(JobExecutionContext context) throws JobExecutionException {
        this.logger.debug("Starting...");
        JobDataMap data = context.getJobDetail().getJobDataMap();
        StockService stockService = (StockService) data.get("service");
        SimpleDateFormat sdfDayOfWeek = new SimpleDateFormat("E");
        SimpleDateFormat sdfHourOfDay = new SimpleDateFormat("HH");
        sdfDayOfWeek.setTimeZone(TimeZone.getTimeZone("EST"));
        sdfHourOfDay.setTimeZone(TimeZone.getTimeZone("EST"));
//        Date currentDate = new Date(123456789L);
        Date currentDate = new Date();
        String dayOfWeek = sdfDayOfWeek.format(currentDate);
        String hourOfDayStr = sdfHourOfDay.format(currentDate);
        Integer hourOfDay = Integer.valueOf(hourOfDayStr);

        if (!dayOfWeek.equalsIgnoreCase("sat") &&
                !dayOfWeek.equalsIgnoreCase("sun") &&
                hourOfDay == 6) {
            this.logger.debug("Day of week: " + dayOfWeek + ", hour of day: " + hourOfDay);

            Date to = new Date();
            Date from = new Date(to.getTime() - (1000L * 60 * 60 * 24 * 7)); // get last 7 days just to capture any down days
            List<String> symbols = StockService.SP_100;

            symbols.forEach((symbol)->{
                try {
                    List<Quote> quotes = stockService.acquire(symbol, from, to);
                    stockService.save(quotes);
                    Thread.sleep(500L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            this.logger.info("Skipping: Day of week: " + dayOfWeek + ", hour of day: " + hourOfDay);
        }
    }
}

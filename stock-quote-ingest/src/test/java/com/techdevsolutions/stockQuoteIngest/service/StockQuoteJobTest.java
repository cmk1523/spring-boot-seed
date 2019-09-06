package com.techdevsolutions.stockQuoteIngest.service;

import com.techdevsolutions.shared.dao.elasticsearch.QuoteElasticsearchDAO;
import com.techdevsolutions.shared.dao.yahoo.YahooFinanceDAO;
import com.techdevsolutions.shared.service.StockService;
import com.techdevsolutions.stockQuoteIngest.services.StockQuoteJob;
import org.junit.Ignore;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class StockQuoteJobTest {
    YahooFinanceDAO yahooFinanceDAO = new YahooFinanceDAO();
    QuoteElasticsearchDAO quoteElasticsearchDAO = new QuoteElasticsearchDAO();
    StockService stockService = new StockService(this.yahooFinanceDAO, this.quoteElasticsearchDAO);
    Scheduler scheduler = new StdSchedulerFactory().getScheduler();

    public StockQuoteJobTest() throws SchedulerException {
    }

    @Ignore
    @Test
    public void test() throws SchedulerException, InterruptedException {
        List<String> stocks = new ArrayList<>(Arrays.asList("aapl","msft"));

        JobDataMap data = new JobDataMap();
        data.put("service", this.stockService);
        data.put("stocks", stocks);

        JobDetail job = JobBuilder.newJob(StockQuoteJob.class)
                .withIdentity("job1", "group1")
                .usingJobData(data)
                .build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(1))
                .build();

        this.scheduler.scheduleJob(job, trigger);

        this.scheduler.start();
        Thread.sleep(5000);
    }
}

package com.techdevsolutions.stockQuoteIngest.services;

import com.techdevsolutions.shared.service.StockService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StockQuoteScheduler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Environment environment;
    private Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    private StockService stockService;

    @Autowired
    public StockQuoteScheduler(Environment environment, StockService stockService) throws SchedulerException {
        this.environment = environment;
        this.stockService = stockService;

//        String stocksStr = this.environment.getProperty("stocks");
//        List<String> stocks = new ArrayList<>(Arrays.asList(stocksStr.split(",")));

        JobDataMap data = new JobDataMap();
        data.put("service", this.stockService);
//        data.put("stocks", stocks);

        JobDetail job = JobBuilder.newJob(StockQuoteJob.class)
                .withIdentity("job1", "group1")
                .usingJobData(data)
                .build();

        // http://www.cronmaker.com/
        // Every minute = "0 1/1 * * * ?"
        // Every hour = "0 0 * ? * *"
        // 10:21pm = "0 21 22 * * ?"

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?")) // every 10 sec
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 18 * * ?")) // every day at 6PM
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 * ? * *")) // every hour
                .forJob(job)
                .build();

        this.scheduler.scheduleJob(job, trigger);

        this.scheduler.start();

    }
}

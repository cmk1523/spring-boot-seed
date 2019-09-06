package com.techdevsolutions.stockQuoteIngest.controllers;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.util.concurrent.RateLimiter;
import com.techdevsolutions.shared.beans.Response;
import com.techdevsolutions.shared.beans.ResponseList;
import com.techdevsolutions.shared.beans.yahoo.Quote;
import com.techdevsolutions.shared.service.StockService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController extends BaseController {
    protected StockService stockService;
    protected Integer RATE_LIMIT = 1; // 1 per second
    protected Cache<String, RateLimiter> userRateLimitCache = Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();
    @Autowired
    public StockController(Environment environment, StockService stockService) {
        super(environment);
        this.stockService = stockService;
    }

    @ResponseBody
    @RequestMapping(value = "quote/{symbols}", method = RequestMethod.GET)
    public Object symbols(HttpServletRequest request, HttpServletResponse response,
                          @PathVariable String symbols
    ) {
        try {
            if (this.checkRateLimiter(request, this.userRateLimitCache, this.RATE_LIMIT)) {
                List<String> symbolList = Arrays.asList(symbols.split(","));
                List<Quote> quotes = this.stockService.acquire(symbolList);
                return new ResponseList(quotes, this.getTimeTook(request), this.getUser(request), new Date());
            } else {
                return this.generateErrorResponse(request, HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }
}
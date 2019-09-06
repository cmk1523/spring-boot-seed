package com.techdevsolutions.stockQuoteIngest.controllers;

import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.util.concurrent.RateLimiter;
import com.techdevsolutions.shared.beans.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;

public class BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Environment environment;

    public BaseController(Environment environment) {
        this.environment = environment;
    }

    public Long getTimeTook(HttpServletRequest request) {
        Long startTime = (Long) request.getAttribute("requestStartTime");
        return new Date().getTime() - startTime;
    }

    public ResponseEntity generateErrorResponse(HttpServletRequest request, HttpStatus status, String message) {
        ErrorResponse errorResponse = new ErrorResponse(request.getServletPath(),
                status.getReasonPhrase(),
                status.toString(),
                message);
        return ResponseEntity.status(status).body(errorResponse);
    }

    public String getUser(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }

    public Boolean checkRateLimiter(HttpServletRequest request, Cache<String, RateLimiter> userRateLimitCache, Integer limit) {
        Authentication authentication = (Authentication) request.getUserPrincipal();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        RateLimiter rateLimiter = userRateLimitCache.getIfPresent(userDetails.getUsername());

        if (rateLimiter == null) {
            rateLimiter = RateLimiter.create(limit);
            userRateLimitCache.put(userDetails.getUsername(), rateLimiter);
        }

        return rateLimiter.tryAcquire();
    }
}

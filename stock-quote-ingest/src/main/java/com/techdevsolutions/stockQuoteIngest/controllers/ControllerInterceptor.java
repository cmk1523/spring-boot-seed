package com.techdevsolutions.stockQuoteIngest.controllers;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class ControllerInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public String getRestURI(HttpServletRequest request) {
        return ControllerInterceptor.GetRestURI(request);
    }

    public static String GetRestURI(HttpServletRequest request) {
        return request.getMethod() + " " + request.getRequestURI();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, InterruptedException {
        this.logger.info(this.getRestURI(request));
        Authentication authentication = (Authentication) request.getUserPrincipal();

        if (authentication == null) {
            response.sendRedirect("/login");
            return false;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        request.getSession().setAttribute("username", userDetails.getUsername());
        request.setAttribute("requestStartTime", new Date().getTime());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
package com.techdevsolutions.messenger.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class ControllerInterceptor extends HandlerInterceptorAdapter {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getRestURI(HttpServletRequest request) {
        return ControllerInterceptor.GetRestURI(request);
    }

    public static String GetRestURI(HttpServletRequest request) {
        return request.getMethod() + " " + request.getRequestURI();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        this.logger.info(this.getRestURI(request));
        Authentication authentication = (Authentication) request.getUserPrincipal();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        request.getSession().setAttribute("username", userDetails.getUsername());
        request.setAttribute("requestStartTime", new Date().getTime());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
package com.techdevsolutions.messenger.controllers;

import com.techdevsolutions.shared.beans.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
}

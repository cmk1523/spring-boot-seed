package com.techdevsolutions.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.logging.Logger;

public class BaseController {
    protected Logger logger = Logger.getLogger(BaseController.class.getName());

    public Long getTimeTook(HttpServletRequest request) {
        Long startTime = (Long) request.getAttribute("requestStartTime");
        return new Date().getTime() - startTime;
    }

}

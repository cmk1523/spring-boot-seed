package com.techdevsolutions.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
@RequestMapping("/")
public class AngularController extends BaseController {

    @Autowired
    public AngularController(Environment environment) {
        super(environment);
    }

    @RequestMapping(value = "/**/{[path:[^\\.]*}")
    public String redirect() {
        // Forward to home page so that route is preserved.
        return "forward:/";
    }

}

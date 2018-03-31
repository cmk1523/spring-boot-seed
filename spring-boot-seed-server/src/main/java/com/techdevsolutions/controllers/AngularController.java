package com.techdevsolutions.controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.logging.Logger;

@Controller
@EnableAutoConfiguration
@RequestMapping("/")
public class AngularController extends BaseController {
    @RequestMapping(value = "/**/{[path:[^\\.]*}")
    public String redirect() {
        // Forward to home page so that route is preserved.
        return "forward:/";
    }

}

package com.techdevsolutions.messenger.controllers;

import com.techdevsolutions.shared.beans.Response;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/app")
public class AppController extends BaseController {

    @Autowired
    public AppController(Environment environment) {
        super(environment);
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object search(HttpServletRequest request, HttpServletResponse response,
        @ApiParam(defaultValue = "true", allowableValues = "true,false")
        @RequestParam Boolean verbose
    ) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("name", environment.getProperty("application.name"));
            map.put("title", environment.getProperty("application.title"));
            map.put("version", environment.getProperty("application.version"));
            map.put("buildNumber", environment.getProperty("application.buildNumber"));
            map.put("buildDateTime", environment.getProperty("application.buildDateTime"));

            Authentication authentication = (Authentication) request.getUserPrincipal();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            Map<String, Object> userObject = new HashMap<>();
            userObject.put("username", userDetails.getUsername());
            userObject.put("authorities", userDetails.getAuthorities());

            map.put("user", userObject);
            return new Response(map, this.getTimeTook(request), this.getUser(request), new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }


//    @ResponseBody
//    @RequestMapping(value = "install", method = RequestMethod.GET)
//    public Object install(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            this.installerService.install();
//            return new Response(null, this.getTimeTook(request));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
//        }
//    }

}
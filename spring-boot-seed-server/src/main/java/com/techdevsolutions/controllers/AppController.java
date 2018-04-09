package com.techdevsolutions.controllers;

import com.techdevsolutions.beans.Response;
import com.techdevsolutions.service.InstallerService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1/app")
public class AppController extends BaseController {
    private Environment environment;
    private InstallerService installerService;

    public AppController(Environment environment, InstallerService installerService) {
        this.environment = environment;
        this.installerService = installerService;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object search(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("name", environment.getProperty("parent-name"));
            map.put("version", environment.getProperty("parent-version"));
            map.put("serverName", environment.getProperty("server-name"));
            map.put("serverVersion", environment.getProperty("server-version"));
            map.put("build", environment.getProperty("build"));
            map.put("buildDateTime", environment.getProperty("buildDateTime"));

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            Map<String, Object> userObject = new HashMap<>();
            userObject.put("username", authentication.getName());
            userObject.put("authorities", userDetails.getAuthorities());

            map.put("user", userObject);
            return new Response(map, this.getTimeTook(request));
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "install", method = RequestMethod.GET)
    public Object install(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.installerService.install();
            return new Response(null, this.getTimeTook(request));
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

}
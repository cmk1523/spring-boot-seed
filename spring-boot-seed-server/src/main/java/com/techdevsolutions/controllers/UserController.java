package com.techdevsolutions.controllers;

import com.techdevsolutions.beans.Response;
import com.techdevsolutions.beans.ResponseList;
import com.techdevsolutions.beans.User;
import com.techdevsolutions.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Response search(HttpServletRequest request) {
        try {
            List<User> list = this.userService.getAll();
            return new ResponseList(list, this.getTimeTook(request));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Response get(HttpServletRequest request, @PathVariable Integer id) {
        try {
            User item = this.userService.get(id);
            return new Response(item, this.getTimeTook(request));
        } catch (NoSuchElementException e) {
            return new Response(e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Response put(HttpServletRequest request, @PathVariable Integer id, @RequestBody User data) {
        try {
            User newItem = this.userService.create(data);
            return new Response(newItem, this.getTimeTook(request));
        } catch (IllegalArgumentException e) {
            return new Response(e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public Response update(HttpServletRequest request, @PathVariable Integer id, @RequestBody User data) {
        try {
            User updatedItem = this.userService.update(data);
            return new Response(updatedItem, this.getTimeTook(request));
        } catch (IllegalArgumentException e) {
            return new Response(e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Response delete(HttpServletRequest request, @PathVariable Integer id) {
        try {
            this.userService.delete(id);
            return new Response(null, this.getTimeTook(request));
        } catch (NoSuchElementException e) {
            return new Response(e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(e.toString());
        }
    }
}
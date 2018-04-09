package com.techdevsolutions.controllers;

import com.techdevsolutions.beans.Response;
import com.techdevsolutions.beans.ResponseList;
import com.techdevsolutions.beans.auditable.User;
import com.techdevsolutions.service.user.UserService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object search(HttpServletRequest request) {
        try {
            List<User> list = this.userService.getAll();
            return new ResponseList(list, this.getTimeTook(request));
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Object get(HttpServletRequest request, @PathVariable Integer id, HttpServletResponse response) {
        try {
            User item = this.userService.get(id);

            if (item != null) {
                return new Response(item, this.getTimeTook(request));
            } else {
                return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR,
                        "UserController - get - Item was not found using ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Object put(HttpServletRequest request, @RequestBody User data) {
        try {
            User newItem = this.userService.create(data);
            return new Response(newItem, this.getTimeTook(request));
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public Object update(HttpServletRequest request, @PathVariable Integer id, @RequestBody User data) {
        try {
            User updatedItem = this.userService.update(data);

            if (updatedItem != null) {
                return new Response(updatedItem, this.getTimeTook(request));
            } else {
                return new Response("Unable to find item: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(HttpServletRequest request, @PathVariable Integer id) {
        try {
            this.userService.delete(id);
            return new Response(null, this.getTimeTook(request));
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }
}
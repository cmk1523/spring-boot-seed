package com.techdevsolutions.controllers;

import com.techdevsolutions.beans.Response;
import com.techdevsolutions.beans.ResponseList;
import com.techdevsolutions.beans.Filter;
import com.techdevsolutions.beans.Search;
import com.techdevsolutions.beans.auditable.User;
import com.techdevsolutions.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public Object search(HttpServletRequest request,
                        @RequestParam(value = "term") Optional<String> term,
                        @RequestParam(value = "size") Optional<Integer> size,
                        @RequestParam(value = "page") Optional<Integer> page,
                        @RequestParam(value = "sort") Optional<String> sort,
                        @RequestParam(value = "order") Optional<String> order,
                        @RequestParam(value = "filters") Optional<String> filters,
                        @RequestParam(value = "filterLogic") Optional<String> filterLogic) {
        try {
            Search search = new Search();
            search.setTerm((term.isPresent()) ? term.get() : "");
            search.setSize((size.isPresent()) ? size.get() : Filter.DEFAULT_SIZE);
            search.setPage((page.isPresent()) ? page.get() : Filter.DEFAULT_PAGE);
            search.setSort((sort.isPresent()) ? sort.get() : Filter.DEFAULT_SORT);
            search.setOrder((order.isPresent()) ? order.get() : Filter.DEFAULT_ORDER);
            search.setFilters((filters.isPresent()) ? filters.get() : "");
            search.setFilterLogic((filterLogic.isPresent()) ? filterLogic.get() : Filter.DEFAULT_FILTER_LOGIC);

            List<User> list = this.userService.search(search);
            return new ResponseList(list, this.getTimeTook(request));
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getAll(HttpServletRequest request,
                         @RequestParam(value = "size") Optional<Integer> size,
                         @RequestParam(value = "page") Optional<Integer> page,
                         @RequestParam(value = "sort") Optional<String> sort,
                         @RequestParam(value = "order") Optional<String> order,
                         @RequestParam(value = "filters") Optional<String> filters,
                         @RequestParam(value = "filterLogic") Optional<String> filterLogic) {
        try {
            Filter filter = new Filter();
            filter.setSize((size.isPresent()) ? size.get() : Filter.DEFAULT_SIZE);
            filter.setPage((page.isPresent()) ? page.get() : Filter.DEFAULT_PAGE);
            filter.setSort((sort.isPresent()) ? sort.get() : Filter.DEFAULT_SORT);
            filter.setOrder((order.isPresent()) ? order.get() : Filter.DEFAULT_ORDER);
            filter.setFilters((filters.isPresent()) ? filters.get() : "");
            filter.setFilterLogic((filterLogic.isPresent()) ? filterLogic.get() : Filter.DEFAULT_FILTER_LOGIC);

            List<User> list = this.userService.get(filter);
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
    public Object delete(HttpServletRequest request, @PathVariable Integer id, @RequestParam(value = "false") Optional<String> delete) {
        try {
            if (delete != null && delete.isPresent()) {
                this.userService.delete(id);
            } else {
                this.userService.remove(id);
            }

            return new Response(null, this.getTimeTook(request));
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }
}
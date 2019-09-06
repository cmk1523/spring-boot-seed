package com.techdevsolutions.messenger.controllers;

import com.techdevsolutions.files.beans.auditable.File;
import com.techdevsolutions.files.service.FileService;
import com.techdevsolutions.shared.beans.Filter;
import com.techdevsolutions.shared.beans.Response;
import com.techdevsolutions.shared.beans.ResponseList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
public class FileController extends BaseController {

    private FileService fileService;

    @Autowired
    public FileController(Environment environment, FileService fileService) {
        super(environment);
        this.fileService = fileService;
    }

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public Object get(HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            this.fileService.scanAndLoadRootDirectory();
            return new Response(null, this.getTimeTook(request), this.getUser(request), new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public Object search(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam(required = false) String term,
                         @RequestParam(required = false) Integer size,
                         @RequestParam(required = false) Integer page,
                         @RequestParam(required = false) String sort,
                         @RequestParam(required = false) String order
    ) {
        try {
            Filter search = new Filter();
            search.setTerm(StringUtils.isNotEmpty(term) ? term : "");
            search.setSize(size != null ? size : Filter.DEFAULT_SIZE);
            search.setPage(page != null ? page : Filter.DEFAULT_PAGE);
            search.setSort(StringUtils.isNotEmpty(sort) ? sort : "");
            search.setOrder(StringUtils.isNotEmpty(order) ? order : "");
            List<File> result = this.fileService.search(search);
            return new ResponseList(result, this.getTimeTook(request), this.getUser(request), new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Object get(HttpServletRequest request, HttpServletResponse response,
                      @PathVariable String id
    ) {
        try {
            File item = this.fileService.get(id);
            return new Response(item, this.getTimeTook(request), this.getUser(request), new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Object create(HttpServletRequest request, HttpServletResponse response,
                      @RequestBody File item
    ) {
        try {
            File result = this.fileService.create(item);
            return new Response(result, this.getTimeTook(request), this.getUser(request), new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object update(HttpServletRequest request, HttpServletResponse response,
                      @RequestBody File item
    ) {
        try {
            File result = this.fileService.update(item);
            return new Response(result, this.getTimeTook(request), this.getUser(request), new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(HttpServletRequest request, HttpServletResponse response,
                      @PathVariable String id
    ) {
        try {
            this.fileService.delete(id);
            return new Response(null, this.getTimeTook(request), this.getUser(request), new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }
}
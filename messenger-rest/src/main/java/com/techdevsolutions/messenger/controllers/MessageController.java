package com.techdevsolutions.messenger.controllers;

import com.techdevsolutions.shared.beans.Response;
import com.techdevsolutions.files.beans.auditable.Message;
import com.techdevsolutions.files.service.MessageService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController extends BaseController {
    protected MessageService messageService;

    @Autowired
    public MessageController(Environment environment, MessageService messageService) {
        super(environment);
        this.messageService = messageService;
    }

    @ResponseBody
    @RequestMapping(value = "encrypt/{password}", method = RequestMethod.POST)
    public Object encrypt(HttpServletRequest request, HttpServletResponse response,
        @PathVariable String password,

        @RequestBody String messageToEncrypt,

        @ApiParam(defaultValue = "true", allowableValues = "true,false")
        @RequestParam Boolean verbose
    ) {
        try {
            Message message = new Message();
            message.setText(messageToEncrypt);
            message = this.messageService.encryptMessage(message, password);

            if (verbose) {
                return new Response(message.getText(), this.getTimeTook(request), this.getUser(request), new Date());
            } else {
                return message.getText();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "decrypt/{password}", method = RequestMethod.POST)
    public Object decrypt(HttpServletRequest request, HttpServletResponse response,
        @PathVariable String password,

        @RequestBody String messageToDecrypt,

        @ApiParam(defaultValue = "true", allowableValues = "true,false")
        @RequestParam Boolean verbose
    ) {
        try {
            Message message = new Message();
            message.setText(messageToDecrypt);
            message = this.messageService.decryptMessage(message, password);

            if (verbose) {
                return new Response(message.getText(), this.getTimeTook(request), this.getUser(request), new Date());
            } else {
                return message.getText();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }
}
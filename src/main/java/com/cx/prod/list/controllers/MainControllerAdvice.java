package com.cx.prod.list.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class MainControllerAdvice {
    private Logger logger = LoggerFactory.getLogger(MainControllerAdvice.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({Exception.class})
    public final String handleException(Exception e, WebRequest req) {
        logger.error("Unhandled exception: " + e.getMessage());
        return "index";
    }
}

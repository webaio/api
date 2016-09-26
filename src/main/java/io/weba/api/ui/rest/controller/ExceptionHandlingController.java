package io.weba.api.ui.rest.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(Exception.class)
    public String handle(Exception exception) {
        return "dsadsadsadsa";
    }
}

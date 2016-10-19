package io.weba.api.ui.rest.exception.handler;

import io.weba.api.ui.rest.GenericExceptionDecorator;
import io.weba.api.ui.rest.exception.http.BadRequestException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadRequestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GenericExceptionDecorator> badRequestExceptionHandler(HttpServletRequest request, Exception e) {
        return new ResponseEntity<>(
                new GenericExceptionDecorator(e.getMessage()), HttpStatus.BAD_REQUEST
        );
    }
}

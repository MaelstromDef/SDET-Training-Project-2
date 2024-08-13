package com.ahuggins.warehousedemo.aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

// This class intercepts common and purposeful exceptions and creates a clean response.
@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleStatusException(ResponseStatusException e){
        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<Object> handleIllegalAccess(IllegalAccessException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
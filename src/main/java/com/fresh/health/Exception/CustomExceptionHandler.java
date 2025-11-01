package com.fresh.health.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler {

    Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> nullPointerException(RuntimeException ex) {
        logger.info(ex.getLocalizedMessage());
        return ResponseEntity.status(400).body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<String> nullPointerException(NullPointerException ex) {
        logger.info(ex.getLocalizedMessage());
        return ResponseEntity.status(400).body(ex.getLocalizedMessage());
    }

}

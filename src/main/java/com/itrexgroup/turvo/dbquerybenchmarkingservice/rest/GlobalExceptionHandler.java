package com.itrexgroup.turvo.dbquerybenchmarkingservice.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception e) {
        return ResponseEntity.ok(e.getCause().getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handle(ConstraintViolationException e) {
        return ResponseEntity.ok(e.getConstraintViolations()
                .stream().map(cv -> cv.getPropertyPath().toString() + ":" + cv.getMessage())
                .collect(Collectors.toList()));
    }
}

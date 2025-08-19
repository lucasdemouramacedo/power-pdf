package com.power.power_pdf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MergeRequestCreationException.class)
    private ResponseEntity<Map<String, Object>> mergeRequestCreationHandler(MergeRequestCreationException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}

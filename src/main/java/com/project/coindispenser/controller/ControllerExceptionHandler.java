package com.project.coindispenser.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.project.coindispenser.dao.CoinsNotAvailableException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CoinsNotAvailableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleException(CoinsNotAvailableException ex) {
        return buildErrorMap(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException(MethodArgumentTypeMismatchException ex) {
        return buildErrorMap(String.format("'%s' is not a valid '%s'", 
                ex.getValue(), ex.getRequiredType().getSimpleName()));
    }

    @ExceptionHandler ({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Map<String, String> handleException(MethodArgumentNotValidException ex) {
        return buildErrorMap(ex.getFieldError().getDefaultMessage());
    }

    private Map<String, String> buildErrorMap(String message) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", message);
        return errorMap;
    }
}

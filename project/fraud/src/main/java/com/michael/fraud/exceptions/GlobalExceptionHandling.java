package com.michael.fraud.exceptions;


import com.michael.fraud.payload.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {

    private static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleMethodGlobalException(Exception ex) {
        return createHttpResponse(BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionResponse> iOException(IOException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return createHttpResponse(BAD_REQUEST, ex.getMessage());
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("timestamp", new Date());
        body.put("statusCode", HttpStatus.BAD_REQUEST.value());
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("messages", errors);
        return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<ExceptionResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new ExceptionResponse(
                httpStatus.value(),
                httpStatus,
                message),
                httpStatus);
    }
}


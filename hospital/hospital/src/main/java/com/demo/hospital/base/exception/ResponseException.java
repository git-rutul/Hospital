package com.demo.hospital.base.exception;

import com.demo.hospital.base.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResponseException extends ResponseEntityExceptionHandler {

    @Autowired
    ResponseHandler responseHandler;

    //common error massage
    @ExceptionHandler(value
            = {  IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {

        String massage = responseHandler.getMessage(ex.getMessage());

        String bodyOfResponse = "This should be application specific";

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", massage);
        body.put("error",HttpStatus.CONFLICT);
        body.put("path",ex.getStackTrace());
        body.put("status",HttpStatus.CONFLICT.value());

//        return handleExceptionInternal(ex, bodyOfResponse,
//                new HttpHeaders(), HttpStatus.CONFLICT, request);
        return handleExceptionInternal(ex, body,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }



}

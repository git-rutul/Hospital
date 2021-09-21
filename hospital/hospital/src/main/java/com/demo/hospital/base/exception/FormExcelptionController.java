package com.demo.hospital.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class FormExcelptionController extends RuntimeException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException error, WebRequest request) {

//        String massage = responseHandler.getMessage(ex.getMessage());

        String bodyOfResponse = "This should be application specific";

        List<FieldError> errors = error.getBindingResult().getFieldErrors();
        ModelMap map = new ModelMap();
        ModelMap errorMap = new ModelMap();
        map.addAttribute("hasErrors", true);
        for (FieldError fieldError : errors) {
            errorMap.addAttribute(fieldError.getField(), fieldError.getDefaultMessage());
        }
        map.addAttribute("bindingErrors", errorMap);

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", map);
        body.put("error", HttpStatus.BAD_REQUEST);
        body.put("path",error.getStackTrace());
        body.put("status",HttpStatus.BAD_REQUEST.value());

//        return handleExceptionInternal(ex, bodyOfResponse,
//                new HttpHeaders(), HttpStatus.CONFLICT, request);
        return new ResponseEntity<Object>(body,  HttpStatus.CONFLICT);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nagarro.s4m.Exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author david
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class BusinessException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<Object> handleEntityNotFound(HttpClientErrorException ex) {
        return new ResponseEntity(new ApiError((short)ex.getRawStatusCode(), ex.getMessage()), HttpStatus.FORBIDDEN);
    }
}

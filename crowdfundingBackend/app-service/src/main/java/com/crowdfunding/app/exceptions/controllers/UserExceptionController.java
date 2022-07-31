package com.crowdfunding.app.exceptions.controllers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.crowdfunding.app.exceptions.UserNotFoundException;

@ControllerAdvice
@Order(value= Ordered.HIGHEST_PRECEDENCE)
public class UserExceptionController {
	
   @ExceptionHandler(value = UserNotFoundException.class)
   public ResponseEntity<Object> userNotFoundException(UserNotFoundException exception) {
      return new ResponseEntity<>("User Not Found Error: "+exception.getMessage(), HttpStatus.NOT_FOUND);
   }
   
}
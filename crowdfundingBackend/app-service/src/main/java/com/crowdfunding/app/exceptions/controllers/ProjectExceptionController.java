package com.crowdfunding.app.exceptions.controllers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.crowdfunding.app.exceptions.ProjectNotFoundException;

@ControllerAdvice
@Order(value= Ordered.HIGHEST_PRECEDENCE)
public class ProjectExceptionController {
	
   @ExceptionHandler(value = ProjectNotFoundException.class)
   public ResponseEntity<Object> projectNotFoundException(ProjectNotFoundException exception) {
      return new ResponseEntity<>("Project Not Found Error: "+exception.getMessage(), HttpStatus.NOT_FOUND);
   }
   
   
   
}
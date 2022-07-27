package com.crowdfunding.app.exceptions.controllers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.crowdfunding.common.exceptions.DataValidationException;
import com.crowdfunding.common.exceptions.RequestNotProperException;

@ControllerAdvice
@Order(value= Ordered.LOWEST_PRECEDENCE)
public class GenericExceptionController {
	
	 @ExceptionHandler(value = DataValidationException.class)
	   public ResponseEntity<Object> userInfoValidationException(DataValidationException exception) {
		      return new ResponseEntity<>("Data Validation Error: "+exception.getMessage(), HttpStatus.NOT_FOUND);
	   }
	 
	 @ExceptionHandler(value = RequestNotProperException.class)
	   public ResponseEntity<Object> requestNotProperExcepion(RequestNotProperException exception) {
		      return new ResponseEntity<>("Request Error: "+exception.getMessage(), HttpStatus.PARTIAL_CONTENT);
	  }
	 
	@ExceptionHandler(value = Exception.class)
	   public ResponseEntity<Object> exception(Exception exception) {
		exception.printStackTrace();
		      return new ResponseEntity<>("Server Error, Please Try After Sometime", HttpStatus.INTERNAL_SERVER_ERROR);
	   }
}

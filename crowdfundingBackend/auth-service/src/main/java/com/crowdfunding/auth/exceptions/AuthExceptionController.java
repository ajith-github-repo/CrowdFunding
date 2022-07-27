package com.crowdfunding.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.crowdfunding.common.exceptions.DataValidationException;
import com.crowdfunding.common.exceptions.RequestNotProperException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class AuthExceptionController {
	
   @ExceptionHandler(value = UserNotFoundException.class)
   public ResponseEntity<Object> userNotFoundException(UserNotFoundException exception) {
      return new ResponseEntity<>("User Not Found Error: "+exception.getMessage(), HttpStatus.NOT_FOUND);
   }
   
   @ExceptionHandler(value = UserAlreadyExistsException.class)
   public ResponseEntity<Object> userAlreadyExistsException(UserAlreadyExistsException exception) {
      return new ResponseEntity<>("User Error: "+exception.getMessage(), HttpStatus.CONFLICT);
   }
   
   @ExceptionHandler(value = DataValidationException.class)
   public ResponseEntity<Object> userInfoValidationException(DataValidationException exception) {
	      return new ResponseEntity<>("Validation Error: "+exception.getMessage(), HttpStatus.BAD_REQUEST);
   }
   
   @ExceptionHandler(value = RequestNotProperException.class)
   public ResponseEntity<Object> requestNotProperExcepion(DataValidationException exception) {
	      return new ResponseEntity<>("Request Error: "+exception.getMessage(), HttpStatus.PARTIAL_CONTENT);
   }
   
   @ExceptionHandler(value = Exception.class)
   public ResponseEntity<Object> exception(Exception exception) {
	   exception.printStackTrace();
	   return new ResponseEntity<>("Server Error, Please Try After Sometime", HttpStatus.INTERNAL_SERVER_ERROR);
   }
}
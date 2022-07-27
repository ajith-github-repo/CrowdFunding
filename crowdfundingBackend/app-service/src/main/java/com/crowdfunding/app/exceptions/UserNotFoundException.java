package com.crowdfunding.app.exceptions;

public class UserNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	
	 public UserNotFoundException(String message) {
	    	super(message);
	    }
	    public UserNotFoundException() {
	    	super();
	    }
}

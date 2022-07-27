package com.crowdfunding.common.exceptions;

public class RequestNotProperException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
    public RequestNotProperException(String message) {
    	super(message);
    }
    public RequestNotProperException() {
    	super();
    }
}

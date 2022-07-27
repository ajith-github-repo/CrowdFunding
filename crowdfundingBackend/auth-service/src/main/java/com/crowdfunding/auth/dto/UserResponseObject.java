package com.crowdfunding.auth.dto;

public class UserResponseObject{

	private Long userId;
	private String firstName;
	private String lastName;
	private String userEmail;
	
	public UserResponseObject() {
		
	}
	
	public UserResponseObject(Long userId,String firstName, String lastName, String userEmail) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
	}




	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}




	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}




	public String getLastName() {
		return lastName;
	}




	public void setLastName(String lastName) {
		this.lastName = lastName;
	}




	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
}

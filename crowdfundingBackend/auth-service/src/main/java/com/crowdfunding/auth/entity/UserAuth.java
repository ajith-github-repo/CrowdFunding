package com.crowdfunding.auth.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_auth")
public class UserAuth implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "email")
	private String userEmail;

	@Column(name = "password")
	private String password;

	@Override
	public int hashCode() {
		return Objects.hash(password, userEmail, userId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAuth other = (UserAuth) obj;
		return Objects.equals(password, other.password)
				&& Objects.equals(userEmail, other.userEmail) && Objects.equals(userId, other.userId);
	}


	public UserAuth() {
		
	}
	
	
	
	public UserAuth(Long userId, String userEmail, String password) {
		super();
		this.userId = userId;
		this.userEmail = userEmail;
		this.password = password;
	}


	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}

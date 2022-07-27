package com.crowdfunding.app.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user_info")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "email")
	private String userEmail;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "first_name")
	private String firstName;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	        name = "users_funded_projects", 
	        joinColumns = { @JoinColumn(name = "user_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "project_id") }
	 )
	private Set<Project> projectsFunded;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(
            name="user_owned_projects",
            joinColumns = @JoinColumn( name="user_id"),
            inverseJoinColumns = @JoinColumn( name="project_id")
        )
	private Set<Project> projectsOwned;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(
            name="user_made_contributions",
            joinColumns = @JoinColumn( name="user_id"),
            inverseJoinColumns = @JoinColumn( name="cntrb_id")
        )
    private Set<Contribution> contributions;	

	public Set<Contribution> getContributions() {
		return contributions;
	}

	public void setContributions(Set<Contribution> contributions) {
		this.contributions = contributions;
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



	
	public Set<Project> getProjectsFunded() {
		return projectsFunded;
	}

	public void setProjectsFunded(Set<Project> projectsFunded) {
		this.projectsFunded = projectsFunded;
	}

	

	public Set<Project> getProjectsOwned() {
		return projectsOwned;
	}


	public void setProjectsOwned(Set<Project> projectsOwned) {
		this.projectsOwned = projectsOwned;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, userEmail, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(userEmail, other.userEmail) && Objects.equals(userId, other.userId);
	}

	

	public User(Long userId, String userEmail, String lastName, String firstName, Set<Project> projectsFunded,
			Set<Project> projectsOwned, Set<Contribution> contributions) {
		super();
		this.userId = userId;
		this.userEmail = userEmail;
		this.lastName = lastName;
		this.firstName = firstName;
		this.projectsFunded = projectsFunded;
		this.projectsOwned = projectsOwned;
		this.contributions = contributions;
	}

	public User() {
		
	}
	
}

package com.crowdfunding.common.dto;

import java.sql.Date;

import com.crowdfunding.common.enums.ProjectStatus;

public class ProjectResponseDTO{

	private Long projectId;
	private String title;
	private String tagline;
	private String description;
	private Long amountRequested;
	private Long amountCollected;
	private Date expireDate;
	private Date creationDate;
	private ProjectStatus status;
	private String tags;
	private String imageUrl;
	private UserResponseDTO innovator;
	private int noOfContributions;
	private int noOfFunders;
    
	
	
	
	
	public int getNoOfContributions() {
		return noOfContributions;
	}



	public void setNoOfContributions(int noOfContributions) {
		this.noOfContributions = noOfContributions;
	}



	public UserResponseDTO getInnovator() {
		return innovator;
	}



	public void setInnovator(UserResponseDTO innovator) {
		this.innovator = innovator;
	}


//	private Set<ContributionResponseDTO> contributions;	
//	private Set<UserResponseDTO> funders;
//
//	public Set<ContributionResponseDTO> getContributions() {
//		return contributions;
//	}
//
//
//
//	public void setContributions(Set<ContributionResponseDTO> contributions) {
//		this.contributions = contributions;
//	}
//
//
//
//	public Set<UserResponseDTO> getFunders() {
//		return funders;
//	}
//
//
//
//	public void setFunders(Set<UserResponseDTO> funders) {
//		this.funders = funders;
//	}



	public Date getExpireDate() {
		return expireDate;
	}



	public String getTags() {
		return tags;
	}



	public void setTags(String tags) {
		this.tags = tags;
	}



	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}



	public Date getCreationDate() {
		return creationDate;
	}



	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public String getImageUrl() {
		return imageUrl;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public String getTagline() {
		return tagline;
	}



	public void setTagline(String tagline) {
		this.tagline = tagline;
	}
	
	




	public int getNoOfFunders() {
		return noOfFunders;
	}



	public void setNoOfFunders(int noOfFunders) {
		this.noOfFunders = noOfFunders;
	}



	public ProjectResponseDTO(Long projectId, String title, String tagline, String description, Long amountRequested,
			Long amountCollected, Date expireDate, Date creationDate, ProjectStatus status, String tags,
			String imageUrl) {
		super();
		this.projectId = projectId;
		this.title = title;
		this.tagline = tagline;
		this.description = description;
		this.amountRequested = amountRequested;
		this.amountCollected = amountCollected;
		this.expireDate = expireDate;
		this.creationDate = creationDate;
		this.status = status;
		this.tags = tags;
		this.imageUrl = imageUrl;
	}
	
	public ProjectResponseDTO() {
		super();
		
	}



	public Long getProjectId() {
		return projectId;
	}



	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Long getAmountRequested() {
		return amountRequested;
	}



	public void setAmountRequested(Long amountRequested) {
		this.amountRequested = amountRequested;
	}



	public Long getAmountCollected() {
		return amountCollected;
	}



	public void setAmountCollected(Long amountCollected) {
		this.amountCollected = amountCollected;
	}



	public ProjectStatus getStatus() {
		return status;
	}



	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	
	
}

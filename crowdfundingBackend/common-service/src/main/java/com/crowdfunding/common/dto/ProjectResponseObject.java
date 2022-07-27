package com.crowdfunding.common.dto;

import java.sql.Date;

public class ProjectResponseObject{

	private Long projectId;
	private String title;
	private String tagLine;
	private String description;
	private Long amountRequested;
	private Long amountCollected;
	private Date expireDate;
	private Date creationDate;
	private ProjectStatus status;
	private String tags;
	private String imageUrl;
	
	
	
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



	public String getTagLine() {
		return tagLine;
	}



	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}


	public ProjectResponseObject(Long projectId, String title, String tagLine, String description, Long amountRequested,
			Long amountCollected, Date expireDate, Date creationDate, ProjectStatus status, String tags,
			String imageUrl) {
		super();
		this.projectId = projectId;
		this.title = title;
		this.tagLine = tagLine;
		this.description = description;
		this.amountRequested = amountRequested;
		this.amountCollected = amountCollected;
		this.expireDate = expireDate;
		this.creationDate = creationDate;
		this.status = status;
		this.tags = tags;
		this.imageUrl = imageUrl;
	}
	
	public ProjectResponseObject() {
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

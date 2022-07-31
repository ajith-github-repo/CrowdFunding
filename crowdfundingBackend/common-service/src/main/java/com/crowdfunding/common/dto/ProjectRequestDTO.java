package com.crowdfunding.common.dto;

import java.util.List;

import com.crowdfunding.common.exceptions.DataValidationException;

public class ProjectRequestDTO{
	
	private String title;
	private String description;
	private Long amountRequested;
	private String expireDate;
	private List<String> tags;
	private String tagline;
	private boolean startGettingFunded;
	private String imageUrl;
	
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public boolean isStartGettingFunded() {
		return startGettingFunded;
	}
	public void setStartGettingFunded(boolean startGettingFunded) {
		this.startGettingFunded = startGettingFunded;
	}
	public String getTagline() {
		return tagline;
	}
	public void setTagline(String tagline) {
		this.tagline = tagline;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
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

	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	
	public ProjectRequestDTO(String title, String description, Long amountRequested, String expireDate,
			List<String> tags, String tagline, boolean startGettingFunded, String imageUrl) {
		super();
		this.title = title;
		this.description = description;
		this.amountRequested = amountRequested;
		this.expireDate = expireDate;
		this.tags = tags;
		this.tagline = tagline;
		this.startGettingFunded = startGettingFunded;
		this.imageUrl = imageUrl;
	}
	public ProjectRequestDTO() {
		
	}
	
}

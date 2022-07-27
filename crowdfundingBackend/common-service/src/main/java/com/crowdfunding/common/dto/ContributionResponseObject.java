package com.crowdfunding.common.dto;

import java.sql.Date;

public class ContributionResponseObject {

    private Long contributionId;
	private Long contributionAmount;
	private Date contributionTime; 
	private Long projectId;
	private Long userId;
	
	public Long getContributionId() {
		return contributionId;
	}
	public void setContributionId(Long contributionId) {
		this.contributionId = contributionId;
	}
	public Long getContributionAmount() {
		return contributionAmount;
	}
	public void setContributionAmount(Long contributionAmount) {
		this.contributionAmount = contributionAmount;
	}
	public Date getContributionTime() {
		return contributionTime;
	}
	public void setContributionTime(Date contributionTime) {
		this.contributionTime = contributionTime;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public ContributionResponseObject(Long contributionId, Long contributionAmount, Date contributionTime,
			Long projectId, Long userId) {
		super();
		this.contributionId = contributionId;
		this.contributionAmount = contributionAmount;
		this.contributionTime = contributionTime;
		this.projectId = projectId;
		this.userId = userId;
	}
	
	public ContributionResponseObject() {
		
	}
	
	
	
}

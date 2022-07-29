package com.crowdfunding.common.dto;

import java.sql.Date;

public class ContributionResponseDTO {

    private Long contributionId;
	private Long contributionAmount;
	private Date contributionTime; 
	private UserResponseDTO contributor;
	private ProjectResponseDTO project;
	
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

	public UserResponseDTO getContributor() {
		return contributor;
	}
	public void setContributor(UserResponseDTO contributor) {
		this.contributor = contributor;
	}
	
	public ProjectResponseDTO getProject() {
		return project;
	}
	public void setProject(ProjectResponseDTO project) {
		this.project = project;
	}
	public ContributionResponseDTO(Long contributionId, Long contributionAmount, Date contributionTime) {
		super();
		this.contributionId = contributionId;
		this.contributionAmount = contributionAmount;
		this.contributionTime = contributionTime;
	}
	
	public ContributionResponseDTO() {
		
	}
	
	
	
}

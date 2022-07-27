package com.crowdfunding.common.dto;

import com.crowdfunding.common.exceptions.DataValidationException;

public class ContributionRequestObject implements RequestObj{

	private Long contributionAmount;
	private Long contributorId;
	private Long projectId;
	public Long getContributionAmount() {
		return contributionAmount;
	}
	public void setContributionAmount(Long contributionAmount) {
		this.contributionAmount = contributionAmount;
	}
	public Long getContributorId() {
		return contributorId;
	}
	public void setContributorId(Long contributorId) {
		this.contributorId = contributorId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public ContributionRequestObject(Long contributionAmount, Long contributorId, Long projectId) {
		super();
		this.contributionAmount = contributionAmount;
		this.contributorId = contributorId;
		this.projectId = projectId;
	}
	
	public ContributionRequestObject() {

	}
	@Override
	public void validateInput() {
		if(this.getContributionAmount() == null || this.getContributionAmount() <= 0L)
			throw new DataValidationException("Contribution cannot be less than zero");
		
		if(this.getContributorId() == null)
			throw new DataValidationException("Contributor Information is Missing");
		
		if(this.getProjectId() == null)
			throw new DataValidationException("Project Info missing from contribution");
		
	}
	
	
}

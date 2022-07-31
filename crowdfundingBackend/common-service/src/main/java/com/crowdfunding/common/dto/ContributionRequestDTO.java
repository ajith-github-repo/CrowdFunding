package com.crowdfunding.common.dto;

public class ContributionRequestDTO{

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
	public ContributionRequestDTO(Long contributionAmount, Long contributorId, Long projectId) {
		super();
		this.contributionAmount = contributionAmount;
		this.contributorId = contributorId;
		this.projectId = projectId;
	}
	
	public ContributionRequestDTO() {

	}
}

package com.crowdfunding.app.entity;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="contribution_info")
public class Contribution {

	@Id
	@Column(name="cntrb_id")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long contributionId;
	
	@Column(name="cntrb_amt")
	private Long contributionAmount;
	
	@Column(name="cntrb_dt_time")
	private Date contributionTime;
	
	//@JsonBackReference(value = "user_contributions")
	@ManyToOne(fetch = FetchType.LAZY)
    private User contributor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonManagedReference(value = "project_contributions")
    private Project project;


	@Column(name="project_id", insertable = false, updatable = false)
	private Long projectId;
	
	
	@Column(name="user_id", insertable = false, updatable = false)
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

	

	public User getContributor() {
		return contributor;
	}

	public void setContributor(User contributor) {
		this.contributor = contributor;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	
	
	
	public Contribution(Long contributionId, Long contributionAmount, Date contributionTime, User contributor,
			Project project, Long userId, Long projectId) {
		super();
		this.contributionId = contributionId;
		this.contributionAmount = contributionAmount;
		this.contributionTime = contributionTime;
		this.contributor = contributor;
		this.project = project;
		this.userId = userId;
		this.projectId = projectId;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(contributionAmount, contributionId, contributionTime, contributor, project, projectId,
				userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contribution other = (Contribution) obj;
		return Objects.equals(contributionAmount, other.contributionAmount)
				&& Objects.equals(contributionId, other.contributionId)
				&& Objects.equals(contributionTime, other.contributionTime)
				&& Objects.equals(contributor, other.contributor) && Objects.equals(project, other.project)
				&& Objects.equals(projectId, other.projectId) && Objects.equals(userId, other.userId);
	}

	public Long getProjectId() {
		return projectId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Contribution() {
		
	}
	
	
	
}

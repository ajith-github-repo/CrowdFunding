package com.crowdfunding.app.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.crowdfunding.common.enums.ProjectStatus;


@Entity
@Table(name = "project_info")
public class Project implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="project_id")
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long projectId;
	
	@Column(name="title")
	private String title;
	
	@Column(name="tagline")
	private String tagline;
	
	@Column(name="desc", length = 5000)
	private String description;
	
	@Column(name="amount_requested")
	private Long amountRequested;
	
	@Column(name="amount_collected")
	private Long amountCollected;
	
	@Column(name="expiry_date")
	private Date expireDate;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="status")
	private ProjectStatus status;
	
	@Column(name="tags")
	private String tags;
	
	@Column(name="image_url")
	private String imageUrl;
	

	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	private User innovator;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
    private Set<Contribution> contributions;	

	@ManyToMany(mappedBy = "projectsFunded",fetch = FetchType.LAZY)
	private Set<User> funders;

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

	public Project(Long projectId, String title, String tagline, String description, Long amountRequested,
			Long amountCollected, Date expireDate, Date creationDate, ProjectStatus status, String tags,
			String imageUrl, User innovator) {
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
		this.innovator = innovator;
	}



	public Set<Contribution> getContributions() {
		return contributions;
	}



	public void setContributions(Set<Contribution> contributions) {
		this.contributions = contributions;
	}


	public Set<User> getFunders() {
		return funders;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(amountCollected, amountRequested, creationDate, description, expireDate, imageUrl,
				innovator, projectId, status, tagline, tags, title);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		return Objects.equals(amountCollected, other.amountCollected)
				&& Objects.equals(amountRequested, other.amountRequested)
				&& Objects.equals(creationDate, other.creationDate) && Objects.equals(description, other.description)
				&& Objects.equals(expireDate, other.expireDate) && Objects.equals(imageUrl, other.imageUrl)
				&& Objects.equals(innovator, other.innovator) && Objects.equals(projectId, other.projectId)
				&& status == other.status && Objects.equals(tagline, other.tagline) && Objects.equals(tags, other.tags)
				&& Objects.equals(title, other.title);
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



	public void setFunders(Set<User> funders) {
		this.funders = funders;
	}



	public Project() {
	
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



	public User getInnovator() {
		return innovator;
	}



	public void setInnovator(User innovator) {
		this.innovator = innovator;
	}



	public ProjectStatus getStatus() {
		return status;
	}



	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	
	
}

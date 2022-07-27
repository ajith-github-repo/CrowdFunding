package com.crowdfunding.app.dto;

import java.util.List;

import com.crowdfunding.app.entity.Project;

public class ProjectResponseObjectPaginated {

	private boolean isNextAvailable;
	private int nextResultStartsAt;
	private List<Project> projects;
	
	public ProjectResponseObjectPaginated(boolean isNextAvailable, int nextResultStartsAt, List<Project> projects) {
		super();
		this.isNextAvailable = isNextAvailable;
		this.nextResultStartsAt = nextResultStartsAt;
		this.projects = projects;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public boolean isNextAvailable() {
		return isNextAvailable;
	}
	public void setNextAvailable(boolean isNextAvailable) {
		this.isNextAvailable = isNextAvailable;
	}
	public int getNextResultStartsAt() {
		return nextResultStartsAt;
	}
	public void setNextResultStartsAt(int nextResultStartsAt) {
		this.nextResultStartsAt = nextResultStartsAt;
	}
	
	public ProjectResponseObjectPaginated(){
	}
	
	
	
}

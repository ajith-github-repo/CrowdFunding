package com.crowdfunding.app.dto;

import java.util.List;

import com.crowdfunding.common.dto.ProjectResponseDTO;

public class ProjectResponseDTOPaginated {

	private boolean isNextAvailable;
	private int nextResultStartsAt;
	private List<ProjectResponseDTO> projects;
	
	public ProjectResponseDTOPaginated(boolean isNextAvailable, int nextResultStartsAt, List<ProjectResponseDTO> projects) {
		super();
		this.isNextAvailable = isNextAvailable;
		this.nextResultStartsAt = nextResultStartsAt;
		this.projects = projects;
	}
	public List<ProjectResponseDTO> getProjects() {
		return projects;
	}
	public void setProjects(List<ProjectResponseDTO> projects) {
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
	
	public ProjectResponseDTOPaginated(){
	}
	
	
	
}

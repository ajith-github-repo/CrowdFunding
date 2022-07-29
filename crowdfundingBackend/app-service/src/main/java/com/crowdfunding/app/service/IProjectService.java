package com.crowdfunding.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.crowdfunding.app.entity.Project;

public interface IProjectService {

	
	public Project getProjectById(Long projectId);

	public List<Project> getAllProjects();

	public Project saveProject(Project project);
	public Page<Project> getAllProjectsPaginated(Pageable page);

	public Page<Project> findAll(Specification<Project> spec, Pageable page);

	public List<Project> getAllProjectsOwnedByCurrentUser(Long userId);
}

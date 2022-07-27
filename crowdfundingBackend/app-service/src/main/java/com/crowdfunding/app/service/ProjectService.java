package com.crowdfunding.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.crowdfunding.app.dao.ProjectDao;
import com.crowdfunding.app.entity.Project;

@Service
public class ProjectService {

	@Autowired
	private ProjectDao projectDao;

	public Project getProjectById(Long projectId) {

		Optional<Project> project = projectDao.findById(projectId);

		if (project.isPresent()) {
			return project.get();
		}

		return null;
	}

	public List<Project> getAllProjects() {

		return projectDao.findAll();
	}

	public Project saveProject(Project project) {
		return projectDao.save(project);
	}

	public Page<Project> getAllProjectsPaginated(Pageable page) {

		return projectDao.findAll(page);
	}

	public Page<Project> findAll(Specification<Project> spec, Pageable page) {
		return projectDao.findAll(spec, page);
	}

	public List<Project> getAllProjectOwnedOfCurrentUser(Long userId) {

		Optional<List<Project>> projectIds = projectDao.findUserOwnedProjects(userId);

		if (projectIds.isPresent()) {
			return projectIds.get();
		}

		return null;

	}

}

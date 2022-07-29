package com.crowdfunding.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.crowdfunding.app.dao.ProjectDao;
import com.crowdfunding.app.entity.Project;
import com.crowdfunding.app.service.IProjectService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectService implements IProjectService{

	@Autowired
	private ProjectDao projectDao;

	public Project getProjectById(Long projectId) {
		log.info("ProjectService::GET_PROJECT_BY_ID Recieved");
		Optional<Project> project = projectDao.findById(projectId);
		if (project.isPresent()) {
			return project.get();
		}
		return null;
	}

	
	public List<Project> getAllProjects() {
		log.info("ProjectService::GET_ALL_PROJECTS Recieved");
		return projectDao.findAll();
	}

	public Project saveProject(Project project) {
		log.info("ProjectService::SAVE_PROJECT Recieved");
		return projectDao.save(project);
	}

	public Page<Project> getAllProjectsPaginated(Pageable page) {
		log.info("ProjectService::GET_ALL_PROJECTS_PAGINATED Recieved");
		return projectDao.findAll(page);
	}

	public Page<Project> findAll(Specification<Project> spec, Pageable page) {
		log.info("ProjectService::FIND_ALL Recieved");
		return projectDao.findAll(spec, page);
	}

	public List<Project> getAllProjectsOwnedByCurrentUser(Long userId) {
		log.info("ProjectService::GET_PROJECT_OWNEDBY_CURRENT_USER Recieved");
		Optional<List<Project>> projectIds = projectDao.findUserOwnedProjects(userId);

		if (projectIds.isPresent()) {
			return projectIds.get();
		}

		return new ArrayList<>();

	}

}

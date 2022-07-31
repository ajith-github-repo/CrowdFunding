package com.crowdfunding.app.service;

import java.util.List;

import com.crowdfunding.app.dto.ProjectResponseDTOPaginated;
import com.crowdfunding.app.entity.Project;
import com.crowdfunding.common.dto.ProjectRequestDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;

public interface IProjectService {

	
	public ProjectResponseDTO fetchProjectById(String projectId);

	public List<ProjectResponseDTO> getAllProjects();

	public ProjectResponseDTO createProject(ProjectRequestDTO projectIO,String token);
	public ProjectResponseDTOPaginated getAllProjectsPaginated(String pagingInfo);

	public ProjectResponseDTOPaginated getAllProjectsPaginatedWithQuery(String pagingInfo,String search);
	
	public ProjectResponseDTO updateProjectStatusToLive(String projectId);

	public Project getProjectById(String projectId);

	Project save(Project project);
}

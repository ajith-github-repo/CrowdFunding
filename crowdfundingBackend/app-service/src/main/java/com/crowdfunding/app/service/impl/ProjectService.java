package com.crowdfunding.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.crowdfunding.app.dao.ProjectDao;
import com.crowdfunding.app.dto.ProjectResponseDTOPaginated;
import com.crowdfunding.app.entity.Project;
import com.crowdfunding.app.entity.User;
import com.crowdfunding.app.exceptions.ProjectNotFoundException;
import com.crowdfunding.app.service.IProjectService;
import com.crowdfunding.app.service.IUserService;
import com.crowdfunding.app.util.ProjectMapper;
import com.crowdfunding.app.util.ProjectSpecificationsBuilder;
import com.crowdfunding.app.util.UserMapper;
import com.crowdfunding.app.util.Util;
import com.crowdfunding.app.validations.IProjectIOValidator;
import com.crowdfunding.common.dto.ProjectRequestDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;
import com.crowdfunding.common.dto.UserResponseDTO;
import com.crowdfunding.common.enums.ProjectStatus;
import com.crowdfunding.common.exceptions.RequestNotProperException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectService implements IProjectService{

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private IProjectIOValidator validator;
	
	@Autowired
	private Util util;

	@Override
	public ProjectResponseDTO fetchProjectById(String projectId) {
		log.info("ProjectService::GET_PROJECT_BY_ID Recieved");
		
		Project project = getProjectById(projectId);
		
		ProjectResponseDTO respProject = projectMapper.toResponseDTO(project);
		
		UserResponseDTO respUser = userMapper.toResponseDTO(project.getInnovator());
		
		
		respProject.setInnovator(respUser);
		respProject.setNoOfContributions(project.getContributions().size());
		respProject.setNoOfFunders(project.getFunders().size());
		
		return respProject;
	}

	public List<ProjectResponseDTO> getAllProjects() {
		log.info("ProjectService::GET_ALL_PROJECTS Recieved");
		
		List<Project> projects = projectDao.findAll();
		
		List<ProjectResponseDTO> projectsRes = getAllProjectsResponse(projects);
		
		return projectsRes;
	}

	public ProjectResponseDTO createProject(ProjectRequestDTO projectIO,String token) {
		log.info("ProjectService::SAVE_PROJECT Recieved");
		
		validator.validate(projectIO);
		Project project = projectMapper.fromRequestDTO(projectIO);
		
		User usr = userService.findCurrentlyLoggedInUser(token);
		
		project.setInnovator(usr);
		
		usr.getProjectsOwned().add(project);
		
		Project prjt = save(project);
		
		if(prjt == null) {log.info("ProjectService::CREATE_PROJECT Project Not Saved");throw new RequestNotProperException("Could not create project");};
		
        ProjectResponseDTO respProject = projectMapper.toResponseDTO(prjt);
		
		UserResponseDTO respUser = userMapper.toResponseDTO(project.getInnovator());
		
		respProject.setInnovator(respUser);
		
		return respProject;
	}

	@Override
	public Project save(Project project) {
		return projectDao.save(project);
	}
	
	@Override
    public Project getProjectById(String projectId) {
    	log.info("ProjectService::GET_PROJECT_BY_ID Recived"+projectId);
    	Long id = validator.validate(projectId);
		Optional<Project> projectOpt = projectDao.findById(id);
		
		if (!projectOpt.isPresent()) {
			log.info("ProjectService::GET_PROJECT_BY_ID Project "+projectId +" Not Found");
			throw new ProjectNotFoundException(projectId +" Not Found");
		}
		
		return projectOpt.get();
	}

	@Override
	public ProjectResponseDTO updateProjectStatusToLive(String projectId) {
		
		Project project = getProjectById(projectId);
		
        project.setStatus(ProjectStatus.OPEN);
		project = projectDao.save(project);
		
		
		ProjectResponseDTO respProject = projectMapper.toResponseDTO(project);
		UserResponseDTO respUser = userMapper.toResponseDTO(project.getInnovator());
		respProject.setInnovator(respUser);
		respProject.setNoOfContributions(project.getContributions().size());
		respProject.setNoOfFunders(project.getFunders().size());
		
		return respProject;
	}
	
	
	public ProjectResponseDTOPaginated getAllProjectsPaginated(String pagingInfo) {
		log.info("ProjectService::GET_ALL_PROJECTS_PAGINATED Recieved");
		
		int[] paging = util.parsePagenationInfo(pagingInfo);
		
		Pageable page= PageRequest.of(paging[1], paging[0]);
		Page<Project> res = projectDao.findAll(page);
		
		List<Project> projects = res.getContent();
		
		List<ProjectResponseDTO> projectsRes = getAllProjectsResponse(projects);
		
		ProjectResponseDTOPaginated projectRO = new ProjectResponseDTOPaginated(!res.isLast(),res.isLast() ? -1 : paging[1] + 1, projectsRes);
		
		
		log.info("ProjectService::GET_ALL_PROJECTS_PAGINATED Returning with "+projectsRes.size());
		
		return projectRO;
	}

	public ProjectResponseDTOPaginated getAllProjectsPaginatedWithQuery(String pagingInfo,String search) {
		log.info("ProjectService::FIND_ALL Recieved");
		
        int[] paging = util.parsePagenationInfo(pagingInfo);
		
		Pageable page= PageRequest.of(paging[1], paging[0]);
		
		ProjectSpecificationsBuilder builder = new ProjectSpecificationsBuilder();
	    Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
	    Matcher matcher = pattern.matcher(search + ",");
	    while (matcher.find()) {
	            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
	    }
	        
	    Specification<Project> spec = builder.build();
	    Page<Project> res = projectDao.findAll(spec,page);
	        
	    List<Project> projects = res.getContent();
	        
	    List<ProjectResponseDTO> projectsRes = getAllProjectsResponse(projects);
			
	    ProjectResponseDTOPaginated projectRO = new ProjectResponseDTOPaginated(!res.isLast(),res.isLast() ? -1 : paging[1] + 1, projectsRes);
		
	    log.info("ProjectService::GET_ALL_PROJECTS_PAGINATED Returning with "+projectsRes.size());
	    
		return projectRO;
	}
	
	private List<ProjectResponseDTO> getAllProjectsResponse(List<Project> projects){
    	 List<ProjectResponseDTO> projectsRes = new ArrayList<ProjectResponseDTO>( projects.size());
    	 
         for ( Project proj : projects ) {
         	
         	ProjectResponseDTO res = projectMapper.toResponseDTO(proj);
         	res.setInnovator(userMapper.toResponseDTO(proj.getInnovator()));
         	res.setNoOfContributions(proj.getContributions().size());
         	res.setNoOfFunders(proj.getFunders().size());
         	projectsRes.add(res);
         }
         return projectsRes;
     }

}

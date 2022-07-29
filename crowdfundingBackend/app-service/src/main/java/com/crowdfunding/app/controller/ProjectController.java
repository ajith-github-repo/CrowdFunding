package com.crowdfunding.app.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crowdfunding.app.dto.ProjectResponseDTOPaginated;
import com.crowdfunding.app.entity.Project;
import com.crowdfunding.app.entity.User;
import com.crowdfunding.app.exceptions.ProjectNotFoundException;
import com.crowdfunding.app.exceptions.UserNotFoundException;
import com.crowdfunding.app.service.IProjectService;
import com.crowdfunding.app.service.IUserService;
import com.crowdfunding.app.util.ContributionMapper;
import com.crowdfunding.app.util.ProjectMapper;
import com.crowdfunding.app.util.ProjectSpecificationsBuilder;
import com.crowdfunding.app.util.UserMapper;
import com.crowdfunding.app.util.Util;
import com.crowdfunding.common.dto.ProjectRequestDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;
import com.crowdfunding.common.dto.UserResponseDTO;
import com.crowdfunding.common.enums.ProjectStatus;
import com.crowdfunding.common.exceptions.RequestNotProperException;
import com.crowdfunding.common.security.JwtConfig;
import com.crowdfunding.common.util.JWTHelper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/projects")
@Slf4j
public class ProjectController {

	@Autowired
	IProjectService projectService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	JwtConfig jwtConfig;
	
	@Autowired
	ProjectMapper projectMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	ContributionMapper contribMapper;
	
	@GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable String projectId){
		log.info("ProjectController::GET_PROJECT_BY_ID Recieved "+projectId);
		Long id;
		try {
			id = Long.parseLong(projectId);
		}catch(NumberFormatException e) {
			log.info("ProjectController::GET_PROJECT_BY_ID Invalid ID "+projectId);
			throw new RequestNotProperException("Invalid Project Id Recieved");
		}
		Project project = projectService.getProjectById(id);
		
		if(project == null) {log.info("ProjectController::GET_PROJECT_BY_ID Project "+projectId +" Not Found"); throw new ProjectNotFoundException(projectId +" Not Found");}
		
		
		ProjectResponseDTO respProject = projectMapper.toResponseDTO(project);
		
		UserResponseDTO respUser = userMapper.toResponseDTO(project.getInnovator());
		
		
		respProject.setInnovator(respUser);
		respProject.setNoOfContributions(project.getContributions().size());
		respProject.setNoOfFunders(project.getFunders().size());
		
		log.info("ProjectController::GET_PROJECT_BY_ID SUCCESS");
		return new ResponseEntity<ProjectResponseDTO>(respProject,HttpStatus.OK);
		
	}
	
	@PutMapping("/{projectId}")
	public ResponseEntity<ProjectResponseDTO> updateProjectStatusToLive(@PathVariable String projectId){
		log.info("ProjectController::UPDATE_PROJECT_STATUS Recieved with ID"+projectId);
		Long id;
		try {
			id = Long.parseLong(projectId);
		}catch(NumberFormatException e) {
			log.info("ProjectController::UPDATE_PROJECT_STATUS Invalid Project ID "+projectId);
			throw new RequestNotProperException("Invalid Project Id Recieved");
		}
		Project project = projectService.getProjectById(id);
		
		if(project == null) {log.info("ProjectController::UPDATE_PROJECT_STATUS Project "+projectId +" Not Found"); throw new ProjectNotFoundException(projectId +" Project Not Found");}
		
		
        project.setStatus(ProjectStatus.OPEN);
		project = projectService.saveProject(project);
		
		
		ProjectResponseDTO respProject = projectMapper.toResponseDTO(project);
		
		UserResponseDTO respUser = userMapper.toResponseDTO(project.getInnovator());

		respProject.setInnovator(respUser);
		respProject.setNoOfContributions(project.getContributions().size());
		respProject.setNoOfFunders(project.getFunders().size());
		
		log.info("ProjectController::UPDATE_PROJECT_STATUS SUCCESS "+projectId);
		return new ResponseEntity<ProjectResponseDTO>(respProject,HttpStatus.OK);
		
	}

	@GetMapping("/all/owned")
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjectsOwnedOfCurrentUser(@RequestHeader(name = "authorization") String tokenHeader){
	
		log.info("ProjectController::GET_ALL_OWNED_PROJECTS Recieved ");
		
		User usr = findCurrentlyLoggedInUser(tokenHeader);
		
		List<Project> projects = projectService.getAllProjectsOwnedByCurrentUser(usr.getUserId());
	
		List<ProjectResponseDTO> projectsRes = getAllProjectsResponse(projects);
		
		
		log.info("ProjectController::GET_ALL_OWNED_PROJECTS SUCCESS Returning "+projectsRes.size() +" Projects");
        return new ResponseEntity<List<ProjectResponseDTO>>(projectsRes,HttpStatus.OK);
	}
	
	@GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects(){
	
		log.info("ProjectController::GET_ALL_PROJECTS SUCCESS Recieved");
		
		List<Project> projects = projectService.getAllProjects();

		List<ProjectResponseDTO> projectsRes = getAllProjectsResponse(projects);
		
		log.info("ProjectController::GET_ALL_PROJECTS SUCCESS Returning with "+projectsRes.size() +" Projects");
		return new ResponseEntity<List<ProjectResponseDTO>>(projectsRes,HttpStatus.OK);
	}
	
	@GetMapping("/paginated")
    public ResponseEntity<ProjectResponseDTOPaginated> getAllProjectsPaginated(@RequestParam(value = "pagingInfo") String pagingInfoStr){
	
		log.info("ProjectController::GET_ALL_PROJECTS_PAGINATED Recieved ");
		
		int[] pagingInfo = Util.parsePagenationInfo(pagingInfoStr);
		
		Pageable page= PageRequest.of(pagingInfo[1], pagingInfo[0]);
		Page<Project> res = projectService.getAllProjectsPaginated(page);
		
		List<Project> projects = res.getContent();
		
		List<ProjectResponseDTO> projectsRes = getAllProjectsResponse(projects);
		
		ProjectResponseDTOPaginated projectRO = new ProjectResponseDTOPaginated(!res.isLast(),res.isLast() ? -1 : pagingInfo[1] + 1, projectsRes);
		
		
		log.info("ProjectController::GET_ALL_PROJECTS_PAGINATED Returning with "+projectsRes.size());
		return new ResponseEntity<ProjectResponseDTOPaginated>(projectRO,HttpStatus.OK);
	}
	
	@GetMapping("/paginated/search")
	 public ResponseEntity<ProjectResponseDTOPaginated> getAllProjectsPaginatedWithQuery(@RequestParam(value = "pagingInfo") String pagingInfoStr,@RequestParam(value = "query") String search) {
		
		log.info("ProjectController::GET_ALL_PROJECTS_PAGINATED_SEARCH Recieved ");
		
        int[] pagingInfo = Util.parsePagenationInfo(pagingInfoStr);
		
		Pageable page= PageRequest.of(pagingInfo[1], pagingInfo[0]);
		
		ProjectSpecificationsBuilder builder = new ProjectSpecificationsBuilder();
	        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
	        Matcher matcher = pattern.matcher(search + ",");
	        while (matcher.find()) {
	            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
	        }
	        
	        Specification<Project> spec = builder.build();
	        Page<Project> res = projectService.findAll(spec,page);
	        
	        List<Project> projects = res.getContent();
	        
	        List<ProjectResponseDTO> projectsRes = getAllProjectsResponse(projects);
			
			ProjectResponseDTOPaginated projectRO = new ProjectResponseDTOPaginated(!res.isLast(),res.isLast() ? -1 : pagingInfo[1] + 1, projectsRes);
			
			log.info("ProjectController::GET_ALL_PROJECTS_PAGINATED_SEARCH Returning with "+projectsRes.size());
			return new ResponseEntity<ProjectResponseDTOPaginated>(projectRO,HttpStatus.OK);
	    }
	
	@PostMapping
	public ResponseEntity<ProjectResponseDTO> createProject(@RequestHeader(name = "authorization") String tokenHeader,@RequestBody ProjectRequestDTO projectIO){
		log.info("ProjectController::CREATE_PROJECT Recieved");
		projectIO.validateInput();
	
		User usr = findCurrentlyLoggedInUser(tokenHeader);
		
		Project project = projectMapper.fromRequestDTO(projectIO);
		
		project.setInnovator(usr);
		
		usr.getProjectsOwned().add(project);
		
		Project prjt = projectService.saveProject(project);
		
		if(prjt == null) {log.info("ProjectController::CREATE_PROJECT Project Not Saved");throw new RequestNotProperException("Could not create project");};
		
        ProjectResponseDTO respProject = projectMapper.toResponseDTO(prjt);
		
		UserResponseDTO respUser = userMapper.toResponseDTO(project.getInnovator());
		
		respProject.setInnovator(respUser);
		
		log.info("ProjectController::CREATE_PROJECT Returning SUCCESS");
		return new ResponseEntity<ProjectResponseDTO>(respProject,HttpStatus.CREATED);
	}
	
	
     private User findCurrentlyLoggedInUser(String tokenHeader) {
    	 log.info("ProjectController::FIND_CURRENTLY_LOGGED_USER Recieved");
    	 
        String userEmail = JWTHelper.getCurrentlyLoggedInUserFromJWT(tokenHeader.replace(jwtConfig.getPrefix(), ""),jwtConfig.getSecret());
		
		Optional<User> userObj = userService.findUserUsingEmail(userEmail);
		
		if(!userObj.isPresent()) {log.info("ProjectController::FIND_CURRENTLY_LOGGED_USER User Not Found, Project Not Saved");throw new UserNotFoundException("User Not found, Couldnt Create Project");};
		
		User usr = userObj.get();
		
		return usr;
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

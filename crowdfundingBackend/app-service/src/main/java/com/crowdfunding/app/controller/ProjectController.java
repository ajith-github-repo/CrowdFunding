package com.crowdfunding.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.crowdfunding.app.service.IProjectService;
import com.crowdfunding.common.dto.ProjectRequestDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/projects")
@Slf4j
public class ProjectController {

	@Autowired
	private IProjectService projectService;
	
	@PostMapping
	public ResponseEntity<ProjectResponseDTO> createProject(@RequestHeader(name = "authorization") String token,@RequestBody ProjectRequestDTO projectIO){
		log.info("ProjectController::CREATE_PROJECT Recieved");
		
        ProjectResponseDTO respProject = projectService.createProject(projectIO, token);
		
		log.info("ProjectController::CREATE_PROJECT Returning SUCCESS");
		return new ResponseEntity<ProjectResponseDTO>(respProject,HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable String projectId){
		log.info("ProjectController::GET_PROJECT_BY_ID Recieved "+projectId);
		
		ProjectResponseDTO project = projectService.fetchProjectById(projectId);
		
		log.info("ProjectController::GET_PROJECT_BY_ID SUCCESS");
		return new ResponseEntity<ProjectResponseDTO>(project,HttpStatus.OK);
		
	}
	
	@PutMapping("/{projectId}")
	public ResponseEntity<ProjectResponseDTO> updateProjectStatusToLive(@PathVariable String projectId){
		log.info("ProjectController::UPDATE_PROJECT_STATUS Recieved with ID"+projectId);
		
		ProjectResponseDTO project = projectService.updateProjectStatusToLive(projectId);
		log.info("ProjectController::UPDATE_PROJECT_STATUS SUCCESS "+projectId);
		return new ResponseEntity<ProjectResponseDTO>(project,HttpStatus.OK);
		
	}
	
	@GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects(){
	
		log.info("ProjectController::GET_ALL_PROJECTS SUCCESS Recieved");
		
		List<ProjectResponseDTO> projects = projectService.getAllProjects();

		log.info("ProjectController::GET_ALL_PROJECTS SUCCESS Returning with "+projects.size() +" Projects");
		return new ResponseEntity<List<ProjectResponseDTO>>(projects,HttpStatus.OK);
	}
	
	@GetMapping("/paginated")
    public ResponseEntity<ProjectResponseDTOPaginated> getAllProjectsPaginated(@RequestParam(value = "pagingInfo") String pagingInfo){
	
		log.info("ProjectController::GET_ALL_PROJECTS_PAGINATED Recieved");
		
		ProjectResponseDTOPaginated res = projectService.getAllProjectsPaginated(pagingInfo);
		
	
		log.info("ProjectController::GET_ALL_PROJECTS_PAGINATED Success ");
		return new ResponseEntity<ProjectResponseDTOPaginated>(res,HttpStatus.OK);
	}
	
	@GetMapping("/paginated/search")
	 public ResponseEntity<ProjectResponseDTOPaginated> getAllProjectsPaginatedWithQuery(@RequestParam(value = "pagingInfo") String pagingInfo,@RequestParam(value = "query") String search) {
		
		log.info("ProjectController::GET_ALL_PROJECTS_PAGINATED_SEARCH Recieved ");
		
        ProjectResponseDTOPaginated res = projectService.getAllProjectsPaginatedWithQuery(pagingInfo, search);
			
		log.info("ProjectController::GET_ALL_PROJECTS_PAGINATED_SEARCH SUCCESS");
		return new ResponseEntity<ProjectResponseDTOPaginated>(res,HttpStatus.OK);
	    }

}

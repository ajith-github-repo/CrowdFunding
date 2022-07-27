package com.crowdfunding.app.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

import com.crowdfunding.app.dto.ProjectRequestObject;
import com.crowdfunding.app.dto.ProjectResponseObjectPaginated;
import com.crowdfunding.app.entity.Contribution;
import com.crowdfunding.app.entity.Project;
import com.crowdfunding.app.entity.User;
import com.crowdfunding.app.exceptions.ProjectNotFoundException;
import com.crowdfunding.app.exceptions.UserNotFoundException;
import com.crowdfunding.app.service.ProjectService;
import com.crowdfunding.app.service.UserService;
import com.crowdfunding.app.util.ProjectHelper;
import com.crowdfunding.app.util.ProjectSpecificationsBuilder;
import com.crowdfunding.common.dto.ProjectStatus;
import com.crowdfunding.common.exceptions.RequestNotProperException;
import com.crowdfunding.common.security.JwtConfig;
import com.crowdfunding.common.util.DateHelper;
import com.crowdfunding.common.util.JWTHelper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/projects")
@Slf4j
public class ProjectController {

	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtConfig jwtConfig;
	
	
	
	@GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable String projectId){
	
		Long id;
		try {
			id = Long.parseLong(projectId);
		}catch(NumberFormatException e) {
			throw new RequestNotProperException("Invalid Project Id Recieved");
		}
		Project project = projectService.getProjectById(id);
		
		if(project == null) throw new ProjectNotFoundException(projectId +" Not Found");
		
		Set<Contribution> contributions = project.getContributions();
		project.setNoOfFunders(contributions.size());
		return new ResponseEntity<Project>(project,HttpStatus.OK);
		
	}
	
	@PutMapping("/{projectId}")
	public ResponseEntity<Project> updateProjectStatusToLive(@PathVariable String projectId){
		
		Long id;
		try {
			id = Long.parseLong(projectId);
		}catch(NumberFormatException e) {
			throw new RequestNotProperException("Invalid Project Id Recieved");
		}
		Project project = projectService.getProjectById(id);
		
		if(project == null) throw new ProjectNotFoundException(projectId +" Not Found");
		
		
        project.setStatus(ProjectStatus.OPEN);
		project = projectService.saveProject(project);
		return new ResponseEntity<Project>(project,HttpStatus.OK);
		
	}

	@GetMapping("/all/owned")
    public ResponseEntity<List<Project>> getAllProjectsOwnedOfCurrentUser(@RequestHeader(name = "authorization") String tokenHeader){
	
		
		User usr = findCurrentlyLoggedInUser(tokenHeader);
		
		List<Project> projects = projectService.getAllProjectOwnedOfCurrentUser(usr.getUserId());
	
		if(projects != null) {
			return new ResponseEntity<List<Project>>(projects,HttpStatus.OK);
		}
		
		throw new ProjectNotFoundException("Couldnt get All Projects Ids Of Current User");
	}
	
	@GetMapping
    public ResponseEntity<List<Project>> getAllProjects(){
	
		List<Project> projects = projectService.getAllProjects();
	
		if(projects != null) {
			return new ResponseEntity<List<Project>>(projects,HttpStatus.OK);
		}
		
		throw new ProjectNotFoundException("Couldnt get All Projects Of Current User");
	}
	
	@GetMapping("/paginated")
    public ResponseEntity<ProjectResponseObjectPaginated> getAllProjectsPaginated(@RequestParam(value = "pagingInfo") String pagingInfoStr){
	
		int[] pagingInfo = ProjectHelper.parsePagenationInfo(pagingInfoStr);
		
		Pageable page= PageRequest.of(pagingInfo[1], pagingInfo[0]);
		Page<Project> res = projectService.getAllProjectsPaginated(page);
		
		List<Project> projects = res.getContent();
		
		ProjectResponseObjectPaginated projectRO = new ProjectResponseObjectPaginated(!res.isLast(),res.isLast() ? -1 : pagingInfo[1] + 1, projects);
		
		return new ResponseEntity<ProjectResponseObjectPaginated>(projectRO,HttpStatus.OK);
	}
	
	@GetMapping("/paginated/search")
	 public ResponseEntity<ProjectResponseObjectPaginated> getAllProjectsPaginatedWithQuery(@RequestParam(value = "pagingInfo") String pagingInfoStr,@RequestParam(value = "query") String search) {
		
        int[] pagingInfo = ProjectHelper.parsePagenationInfo(pagingInfoStr);
		
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
			
			ProjectResponseObjectPaginated projectRO = new ProjectResponseObjectPaginated(!res.isLast(),res.isLast() ? -1 : pagingInfo[1] + 1, projects);
			
			return new ResponseEntity<ProjectResponseObjectPaginated>(projectRO,HttpStatus.OK);
	    }
	
	@PostMapping
	public ResponseEntity<Project> createProject(@RequestHeader(name = "authorization") String tokenHeader,@RequestBody ProjectRequestObject projectIO){
	
		projectIO.validateInput();
	
		User usr = findCurrentlyLoggedInUser(tokenHeader);
		
		Project project = new Project();
		
		if(projectIO.getTags() != null) {
			Optional<String> resp = projectIO.getTags().stream().reduce((acc,val)-> acc+","+val);
			String tags = resp.isPresent() ? resp.get() : "";
			project.setTags(tags);
		}else project.setTags("");
		
		
		project.setStatus(projectIO.isStartGettingFunded() ? ProjectStatus.OPEN : ProjectStatus.PENDING);
		project.setAmountRequested(projectIO.getAmountRequested());
		project.setDescription(projectIO.getDescription());
		project.setTitle(projectIO.getTitle());
		project.setAmountCollected(0L);
        project.setCreationDate(DateHelper.convertDateToSQLDate(LocalDate.now().toString()));
		project.setExpireDate(DateHelper.convertDateToSQLDate(projectIO.getExpireDate()));
		project.setTagLine(projectIO.getTagline());
		project.setImageUrl(projectIO.getImageUrl());
		
		project.setInnovator(usr);
		
		usr.getProjectsOwned().add(project);
		
		Project prjt = projectService.saveProject(project);

		return new ResponseEntity<Project>(prjt,HttpStatus.CREATED);
	}
	
	
     private User findCurrentlyLoggedInUser(String tokenHeader) {
		
        String userEmail = JWTHelper.getCurrentlyLoggedInUserFromJWT(tokenHeader.replace(jwtConfig.getPrefix(), ""),jwtConfig.getSecret());
		
		Optional<User> userObj = userService.findUserUsingEmail(userEmail);
		
		if(!userObj.isPresent()) throw new UserNotFoundException("User Not found, Couldnt Create Project");
		
		User usr = userObj.get();
		
		return usr;
	}
}

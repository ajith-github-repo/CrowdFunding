package com.crowdfunding.app.unittests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crowdfunding.app.dao.ProjectDao;
import com.crowdfunding.app.entity.Contribution;
import com.crowdfunding.app.entity.Project;
import com.crowdfunding.app.entity.User;
import com.crowdfunding.app.exceptions.ProjectNotFoundException;
import com.crowdfunding.app.service.IUserService;
import com.crowdfunding.app.service.impl.ProjectService;
import com.crowdfunding.app.util.ProjectMapper;
import com.crowdfunding.app.util.UserMapper;
import com.crowdfunding.app.validations.IProjectIOValidator;
import com.crowdfunding.common.dto.ProjectRequestDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;
import com.crowdfunding.common.dto.UserResponseDTO;
import com.crowdfunding.common.enums.ProjectStatus;
import com.crowdfunding.common.exceptions.RequestNotProperException;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceUnitTest {

	@Mock
	private IUserService userService;
	
	@Mock
	private ProjectMapper projectMapper;
	
	@Mock
	private UserMapper userMapper;

	@Mock
	private ProjectDao projectDao;
	
	@Mock
	private IProjectIOValidator validator;

	@InjectMocks
	private ProjectService projectService;
	
	static List<Project> projects;
	static User user;
	static UserResponseDTO userResDTO;
	static ProjectResponseDTO projectResDTO;
	static Project project;
	static Project projectCreate;
	static ProjectRequestDTO projectRequestDTO;
	static User savedUser;
	static Project savedProject;
	static ProjectResponseDTO projectCreateResDTO;
	
	@BeforeAll
	public static void setupTests() {
		
		Date currentDate = Date.valueOf(LocalDate.now().toString());
		Date expiryDate = Date.valueOf(LocalDate.now().plusDays(5).toString());	
		
		String expiryDateStr = LocalDate.now().plusDays(5).toString();
		projects = new ArrayList<>();
		
		project = new Project(1L, "Skippi Pops", "Its a first of its kind ice pops", "Skippi Ice Pops, India's First ice pop brand is made with 100% RO water", 100L, 10L,currentDate,expiryDate,ProjectStatus.OPEN,"ice,pops", "img");	
		
		List<String> tags = new ArrayList<>();
		tags.add("ice");
		tags.add("pops");
		
		savedUser = new User(1L,"ajith@gmail.com","S","Ajith");
		 
		projectRequestDTO = new ProjectRequestDTO("Skippi Pops", "Skippi Ice Pops, India's First ice pop brand is made with 100% RO water", 100L,expiryDateStr,tags, "Its a first of its kind ice pops",true,"img");
		
		projectCreate = new Project(null, "Skippi Pops", "Its a first of its kind ice pops", "Skippi Ice Pops, India's First ice pop brand is made with 100% RO water", 100L, 10L,currentDate,expiryDate,ProjectStatus.OPEN,"ice,pops", "img");	
		
		projectCreate.setInnovator(savedUser);
		savedUser.getProjectsOwned().add(projectCreate);
		
        savedProject = new Project(1L, "Skippi Pops", "Its a first of its kind ice pops", "Skippi Ice Pops, India's First ice pop brand is made with 100% RO water", 100L, 10L,currentDate,expiryDate,ProjectStatus.OPEN,"ice,pops", "img");	
		
        savedProject.setInnovator(savedUser);
		savedUser.getProjectsOwned().add(savedProject);
		
		
		user = new User(null,"ajith@gmail.com","S","Ajith");
		userResDTO = new UserResponseDTO(1L,"ajith@gmail.com", "S","Ajith");
		 
		User user1 = new User(null,"arun@gmail.com","S","Arun");
		
		Set<User> funders = new HashSet<>();
		funders.add(user1);
		
		Set<Contribution> contributions = new HashSet<>();
		Contribution contribution = new Contribution(1L, 100L, Date.valueOf(LocalDate.now().toString()));
		//contribution.setProject(project);
		contributions.add(contribution);
		project.setInnovator(user);
		project.setContributions(contributions);
		project.setFunders(funders);
		
		
		projects.add(project);
		
		
		projectResDTO = new ProjectResponseDTO(1L, "Skippi Pops", "Its a first of its kind ice pops", "Skippi Ice Pops, India's First ice pop brand is made with 100% RO water", 100L, 10L,currentDate,expiryDate,ProjectStatus.OPEN,"ice,pops", "img");
	    projectResDTO.setNoOfFunders(funders.size());
	    projectResDTO.setNoOfContributions(contributions.size());
	    
	    projectCreateResDTO = new ProjectResponseDTO(1L, "Skippi Pops", "Its a first of its kind ice pops", "Skippi Ice Pops, India's First ice pop brand is made with 100% RO water", 100L, 10L,currentDate,expiryDate,ProjectStatus.OPEN,"ice,pops", "img");
		projectCreateResDTO.setInnovator(userResDTO);   
	}
	
	@Test
	public void testCreateProjectTestSuccess() {
		
		String token = "";
		
		Mockito.doNothing().when(validator).validate(projectRequestDTO);
	    Mockito.when(projectMapper.fromRequestDTO(projectRequestDTO)).thenReturn(projectCreate);
	    Mockito.when(userService.findCurrentlyLoggedInUser(token)).thenReturn(savedUser);
	    Mockito.when(projectDao.save(projectCreate)).thenReturn(savedProject);
	    
	    Mockito.when(projectMapper.toResponseDTO(savedProject)).thenReturn(projectCreateResDTO);
	    Mockito.when(userMapper.toResponseDTO(savedUser)).thenReturn(userResDTO);
	    
        ProjectResponseDTO fromService = projectService.createProject(projectRequestDTO, token);
		
		assertThat(fromService).isNotNull().isEqualTo(projectCreateResDTO);
	    
	}
	
	@Test
	public void testCreateProjectTestFailure() {
		
		String token = "";
		
		Mockito.doNothing().when(validator).validate(projectRequestDTO);
	    Mockito.when(projectMapper.fromRequestDTO(projectRequestDTO)).thenReturn(projectCreate);
	    Mockito.when(userService.findCurrentlyLoggedInUser(token)).thenReturn(savedUser);
	    Mockito.when(projectDao.save(projectCreate)).thenReturn(null);
	    
		
        assertThrows(RequestNotProperException.class,()-> this.projectService.createProject(projectRequestDTO, token));
	    
	}
	
	
	@Test
	public void testAllProjectsTestSuccess() {
		
		Mockito.when(this.projectDao.findAll()).thenReturn(projects);
		Mockito.when(this.userMapper.toResponseDTO(user)).thenReturn(userResDTO);
		Mockito.when(this.projectMapper.toResponseDTO(project)).thenReturn(projectResDTO);
		
		List<ProjectResponseDTO> projectsRes = this.projectService.getAllProjects();
		
		assertThat(projectsRes).isNotNull().hasSize(1);
	}
	
	@Test
	public void testGetProjectByIdSuccess() {
		
		Date currentDate = Date.valueOf(LocalDate.now().toString());
		Date expiryDate = Date.valueOf(LocalDate.now().plusDays(5).toString());
		
		String projectId = "1";
		
		Project proj = new Project(1L, "Skippi Pops", "Its a first of its kind ice pops", "Skippi Ice Pops, India's First ice pop brand is made with 100% RO water", 100L, 10L,currentDate,expiryDate,ProjectStatus.OPEN,"ice,pops", "img");
		
		Mockito.when(this.validator.validate(projectId)).thenReturn(1L);
		
		Mockito.when(this.projectDao.findById(1L)).thenReturn(Optional.of(proj));
		
		Project recivedProj = projectService.getProjectById(projectId);
		
		assertThat(recivedProj).isNotNull().isEqualTo(proj);
		
	}
	
	@Test
	public void testGetProjectByIdFailure() {
		
		Date currentDate = Date.valueOf(LocalDate.now().toString());
		Date expiryDate = Date.valueOf(LocalDate.now().plusDays(5).toString());
		
		String projectId = "1";
		
		Project proj = new Project(1L, "Skippi Pops", "Its a first of its kind ice pops", "Skippi Ice Pops, India's First ice pop brand is made with 100% RO water", 100L, 10L,currentDate,expiryDate,ProjectStatus.OPEN,"ice,pops", "img");
		
		Mockito.when(this.validator.validate(projectId)).thenReturn(1L);
		
		Mockito.when(this.projectDao.findById(1L)).thenReturn(Optional.ofNullable(null));
		
		assertThrows(ProjectNotFoundException.class,()-> this.projectService.getProjectById(projectId));
		
	}
}

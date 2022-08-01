package com.crowdfunding.app.unittests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crowdfunding.app.dao.UserDao;
import com.crowdfunding.app.entity.Contribution;
import com.crowdfunding.app.entity.Project;
import com.crowdfunding.app.entity.User;
import com.crowdfunding.app.exceptions.UserNotFoundException;
import com.crowdfunding.app.service.impl.UserService;
import com.crowdfunding.app.util.ContributionMapper;
import com.crowdfunding.app.util.ProjectMapper;
import com.crowdfunding.app.util.UserMapper;
import com.crowdfunding.app.validations.IUserIOValidator;
import com.crowdfunding.common.dto.ContributionResponseDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;
import com.crowdfunding.common.dto.UserRequestDTO;
import com.crowdfunding.common.dto.UserResponseDTO;
import com.crowdfunding.common.enums.ProjectStatus;
import com.crowdfunding.common.exceptions.RequestNotProperException;
import com.crowdfunding.common.security.JwtConfig;
import com.crowdfunding.common.util.JWTHelper;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

	
	@Mock
	private UserDao userDao;
	
	@Mock
	private UserMapper userMapper;
	
	@Mock
	private ProjectMapper projMapper;
	
	@Mock
	private ContributionMapper contribMapper;
	
	@Mock
	private JwtConfig jwtConfig;
	
	@Mock
	private IUserIOValidator validator;
	
	@Mock
	private JWTHelper jwtHelper;
	
	@InjectMocks
	private UserService userService;
	
	static UserRequestDTO userReqDTO;
	static User user;
	static User savedUser;
	static User savedUserWithContributions;
	static User savedUserWithProjects;
	static UserResponseDTO userResponseDTO;
	static Contribution contribution;
	static Project project;
	static ContributionResponseDTO contributionResponseDTO;
	static ProjectResponseDTO projectResponseDTO;
	static Set<ContributionResponseDTO> contributionsResponseDTO;
	
	@BeforeAll
	public static void setupTests() {
		
	   Date currentDate = Date.valueOf(LocalDate.now().toString());
	   Date expiryDate = Date.valueOf(LocalDate.now().plusDays(5).toString());	
       userReqDTO = new UserRequestDTO(null, "Ajith", "S", "ajith@gmail.com", "");
       project = new Project(1L, "Skippi Pops", "Its a first of its kind ice pops", "Skippi Ice Pops, India's First ice pop brand is made with 100% RO water", 100L, 10L,currentDate,expiryDate,ProjectStatus.OPEN,"ice,pops", "img");	
	   user = new User(null,"ajith@gmail.com","S","Ajith");
		
	   savedUser = new User(1L,"ajith@gmail.com","S","Ajith");
	   userResponseDTO = new UserResponseDTO(1L,"ajith@gmail.com", "S","Ajith");
		
	   savedUserWithContributions = new User(1L,"ajith@gmail.com","S","Ajith");
	   
	   savedUserWithProjects = new User(1L,"ajith@gmail.com","S","Ajith");
	   
	   
	   Set<Project> projects = new HashSet<>();
	   projects.add(project);
	   savedUserWithProjects.setProjectsOwned(projects);
	   
	   
	   Set<Contribution> contributions = new HashSet<>();
	   contribution = new Contribution(1L, 100L, Date.valueOf(LocalDate.now().toString()));
	   contribution.setProject(project);
	   contributions.add(contribution);
	   savedUserWithContributions.setContributions(contributions);
	  
	   contributionResponseDTO = new ContributionResponseDTO(1L, 100L, expiryDate);
	   
	   projectResponseDTO = new ProjectResponseDTO(1L, "Skippi Pops", "Its a first of its kind ice pops", "Skippi Ice Pops, India's First ice pop brand is made with 100% RO water", 100L, 10L,currentDate,expiryDate,ProjectStatus.OPEN,"ice,pops", "img");
	   savedUserWithContributions.setContributions(contributions);
	   
	   contributionResponseDTO.setContributor(userResponseDTO);
	   contributionResponseDTO.setProject(projectResponseDTO);
	  
	   contributionsResponseDTO = new HashSet<>();
	   contributionsResponseDTO.add(contributionResponseDTO);
	}
	
	
	
	@Test
	public void getUserDetailsTestSuccess() {
		
		String userId = "1";
		Long userIdL = 1L;
		
		Mockito.when(validator.validate(userId)).thenReturn(userIdL);
		Mockito.when(this.userDao.findById(userIdL)).thenReturn(Optional.of(savedUser));
		
		Mockito.when(userMapper.toResponseDTO(savedUser)).thenReturn(userResponseDTO);
		
		UserResponseDTO fromService = this.userService.getUserDetails(userId);
		
		assertThat(fromService).isNotNull().isEqualTo(userResponseDTO);
	}
	
	@Test
	public void getUserDetailsTestFailure() {
		
		String userId = "1";
		Long userIdL = 1L;
		
		Mockito.when(validator.validate(userId)).thenReturn(userIdL);
		Mockito.when(this.userDao.findById(userIdL)).thenReturn(Optional.ofNullable(null));
	
		assertThrows(UserNotFoundException.class,()-> this.userService.getUserDetails(userId));
	}
	
	
	@Test
	public void saveUserTestSuccess() {

		Mockito.when(this.userMapper.fromRequestDTO(userReqDTO)).thenReturn(user);
		Mockito.doNothing().when(validator).validate(userReqDTO);
		
		Mockito.when(this.userDao.save(user)).thenReturn(savedUser);
		
		Mockito.when(this.userMapper.toResponseDTO(savedUser)).thenReturn(userResponseDTO);
		
		UserResponseDTO fromService = this.userService.saveUser(userReqDTO);
		assertThat(fromService).isNotNull().isEqualTo(userResponseDTO);
	}
	
	@Test
	public void saveUserTestFailure() {
		
		Mockito.when(this.userMapper.fromRequestDTO(userReqDTO)).thenReturn(user);
		Mockito.doNothing().when(validator).validate(userReqDTO);
		
		Mockito.when(this.userDao.save(user)).thenReturn(null);
		
		assertThrows(RequestNotProperException.class,()-> this.userService.saveUser(userReqDTO));
	}
	
	@Test
	public void getUserProjectsTestSuccess() {
		String userId = "1";
		Long userIdL = 1L;
		String token = "";
		String jwtSecret = "xyz";
		String emailId = "ajith@gmail.com";
		
		Mockito.when(validator.validate(userId)).thenReturn(userIdL);
		
		
		Mockito.when(jwtConfig.getSecret()).thenReturn("xyz");
		Mockito.when(jwtConfig.getPrefix()).thenReturn("");
		Mockito.when(jwtHelper.getCurrentlyLoggedInUserFromJWT(token,jwtSecret)).thenReturn(emailId);
		
		Mockito.when(this.userDao.findUserUsingEmail(emailId)).thenReturn(Optional.of(savedUserWithProjects));
		
		Mockito.when(projMapper.toResponseDTO(project)).thenReturn(projectResponseDTO);
		
		Set<ProjectResponseDTO> fromService = this.userService.getAllUserProjects(token, userId);
		
		assertThat(fromService).isNotNull().hasSize(1);
		
	}
	
	@Test
	public void getUserProjectsTestFailure() {
		String userId = "2";
		Long userIdL = 2L;
		String token = "";
		String jwtSecret = "xyz";
		String emailId = "ajith@gmail.com";
		
		Mockito.when(validator.validate(userId)).thenReturn(userIdL);
		
		
		Mockito.when(jwtConfig.getSecret()).thenReturn("xyz");
		Mockito.when(jwtConfig.getPrefix()).thenReturn("");
		Mockito.when(jwtHelper.getCurrentlyLoggedInUserFromJWT(token,jwtSecret)).thenReturn(emailId);
		Mockito.when(this.userDao.findUserUsingEmail(emailId)).thenReturn(Optional.of(savedUserWithProjects));
		
		assertThrows(RequestNotProperException.class,()-> this.userService.getAllUserProjects(token,userId));
		
	}
	
	@Test
	public void getUserContributionsTestSuccess() {
		
		String userId = "1";
		Long userIdL = 1L;
		String token = "";
		String jwtSecret = "xyz";
		String emailId = "ajith@gmail.com";
		
		Mockito.when(validator.validate(userId)).thenReturn(userIdL);
		
		
		Mockito.when(jwtConfig.getSecret()).thenReturn("xyz");
		Mockito.when(jwtConfig.getPrefix()).thenReturn("");
		Mockito.when(jwtHelper.getCurrentlyLoggedInUserFromJWT(token,jwtSecret)).thenReturn(emailId);
		
		Mockito.when(this.userDao.findUserUsingEmail(emailId)).thenReturn(Optional.of(savedUserWithContributions));
		
		Mockito.when(contribMapper.toResponseDTO(contribution)).thenReturn(contributionResponseDTO);
		Mockito.when(projMapper.toResponseDTO(project)).thenReturn(projectResponseDTO);
		
		Set<ContributionResponseDTO> fromService = this.userService.getUserContributions(token, userId);
		
		assertThat(fromService).isNotNull().hasSize(1);
	}
	
	@Test
	public void getUserContributionsTestFailure() {
		
		String userId = "2";
		Long userIdL = 2L;
		String token = "";
		String jwtSecret = "xyz";
		String emailId = "ajith@gmail.com";
		
		Mockito.when(validator.validate(userId)).thenReturn(userIdL);
		
		
		Mockito.when(jwtConfig.getSecret()).thenReturn("xyz");
		Mockito.when(jwtConfig.getPrefix()).thenReturn("");
		Mockito.when(jwtHelper.getCurrentlyLoggedInUserFromJWT(token,jwtSecret)).thenReturn(emailId);
		
		Mockito.when(this.userDao.findUserUsingEmail(emailId)).thenReturn(Optional.of(savedUserWithContributions));
		
		assertThrows(RequestNotProperException.class,()-> this.userService.getUserContributions(token,userId));
	}
	
	@Test
	public void findCurrentlyLoggedInUserTestSuccess() {
		
		String token = "";
		String jwtSecret = "xyz";
		String emailId = "ajith@gmail.com";
		Mockito.when(jwtConfig.getSecret()).thenReturn("xyz");
		Mockito.when(jwtConfig.getPrefix()).thenReturn("");
		Mockito.when(jwtHelper.getCurrentlyLoggedInUserFromJWT(token,jwtSecret)).thenReturn(emailId);
		
		Mockito.when(this.userDao.findUserUsingEmail(emailId)).thenReturn(Optional.of(savedUser));
		
		User fromService= this.userService.findCurrentlyLoggedInUser(token);
		
		assertThat(fromService).isNotNull().isEqualTo(savedUser);
		
	}
	
	@Test
	public void findCurrentlyLoggedInUserTestFailure() {
		
		String token = "";
		String jwtSecret = "xyz";
		String emailId = "ajith@gmail.com";
		Mockito.when(jwtConfig.getSecret()).thenReturn("xyz");
		Mockito.when(jwtConfig.getPrefix()).thenReturn("");
		Mockito.when(jwtHelper.getCurrentlyLoggedInUserFromJWT(token,jwtSecret)).thenReturn(emailId);
		
		Mockito.when(this.userDao.findUserUsingEmail(emailId)).thenReturn(Optional.ofNullable(null));
		
		assertThrows(UserNotFoundException.class,()-> this.userService.findCurrentlyLoggedInUser(token));
		
	}
	

	
}

package com.crowdfunding.app.unittests;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crowdfunding.app.dao.ContributionDao;
import com.crowdfunding.app.entity.User;
import com.crowdfunding.app.service.IContributionService;

@ExtendWith(MockitoExtension.class)
public class ContributionServiceUnitTest {
	
	
	@Mock
	ContributionDao dao;
	
	
	@InjectMocks
	IContributionService contributionService;
	 
	
	@Test
	void createContributionTest() {
		
		final Long contributionId = 1L;
		final User user = new User( 1L, "ajith@gmail.com", "S", "Ajith", null, null, null);
	
		Date currentDate = new Date(System.currentTimeMillis());
		//final Project project = new Project(1L,"Innovation","Innovation","Innovation Innovation Innovation Innovation",100L,1000L,currentDate,currentDate,ProjectStatus.PENDING,"cool","img",user);
		 
		//Contribution contrib = new Contribution(1L, 10L, currentDate, user, project, contributionId, contributionId)
	
		
		
	}

}

package com.crowdfunding.app.unittests;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crowdfunding.app.dao.ContributionDao;
import com.crowdfunding.app.entity.Contribution;
import com.crowdfunding.app.entity.Project;
import com.crowdfunding.app.entity.User;
import com.crowdfunding.app.service.impl.ContributionService;
import com.crowdfunding.common.enums.ProjectStatus;

@ExtendWith(MockitoExtension.class)
public class ContributionServiceUnitTest {
	
	
	@Mock
	ContributionDao dao;
	
	
	@InjectMocks
	ContributionService contributionService;
	 
	
	@Test
	void createContributionTest() {
		
//		final Long contributionId = 1L;
//		final User user = new User( 1L, "ajith@gmail.com", "S", "Ajith", null, null, null);
//	
//		Date currentDate = new Date(System.currentTimeMillis());
//		final Project project = new Project(1L,"Innovation","Innovation","Innovation Innovation Innovation Innovation",100L,1000L,currentDate,currentDate,ProjectStatus.PENDING,"cool","img",user);
//		 
//		Contribution contrib = new Contribution(contributionId, 10L, currentDate, user, project);
//		
//		
//		Mockito.when(this.dao.save(contrib)).thenReturn(contrib);
//		
//		
//		Contribution fromService = this.contributionService.createContribution(contrib);
//		
//		assertThat(fromService).isNotNull().isEqualTo(contrib);
//	   	
		
	
		
		
	}

}

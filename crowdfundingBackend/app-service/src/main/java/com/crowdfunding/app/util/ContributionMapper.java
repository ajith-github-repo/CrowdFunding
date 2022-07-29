package com.crowdfunding.app.util;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.crowdfunding.app.entity.Contribution;
import com.crowdfunding.common.dto.ContributionRequestDTO;
import com.crowdfunding.common.dto.ContributionResponseDTO;
import com.crowdfunding.common.util.DateHelper;

@Component
public class ContributionMapper {

	public ContributionResponseDTO toResponseDTO(Contribution contrib) {
		
		if(contrib == null) return null;
		
		ContributionResponseDTO respObj = new ContributionResponseDTO();
		
		respObj.setContributionId(contrib.getContributionId());
		respObj.setContributionAmount(contrib.getContributionAmount());
		respObj.setContributionTime(contrib.getContributionTime());
		
		return respObj;
		
	}
	
	
  public Contribution fromRequestDTO(ContributionRequestDTO contribReqObj) {
		
	  if(contribReqObj == null) return null; 
	  
	  Contribution contrib = new Contribution();
		
		contrib.setContributionAmount(contribReqObj.getContributionAmount());
		contrib.setContributionTime(DateHelper.convertDateToSQLDate(LocalDate.now().toString()));
		
		return contrib;
		
	}
	
}

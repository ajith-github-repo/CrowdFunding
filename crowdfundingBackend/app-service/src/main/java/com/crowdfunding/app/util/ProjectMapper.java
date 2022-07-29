package com.crowdfunding.app.util;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.crowdfunding.app.entity.Project;
import com.crowdfunding.common.dto.ProjectRequestDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;
import com.crowdfunding.common.enums.ProjectStatus;
import com.crowdfunding.common.exceptions.RequestNotProperException;
import com.crowdfunding.common.util.DateHelper;

@Component
public class ProjectMapper {
	
	public ProjectResponseDTO toResponseDTO(Project project) {
		
		if(project == null) return null;
		
		ProjectResponseDTO resp = new ProjectResponseDTO();
	    resp.setTitle(project.getTitle());
	    resp.setDescription(project.getDescription());
	    resp.setTagLine(project.getTagLine());
	    resp.setAmountCollected(project.getAmountCollected());
	    resp.setAmountRequested(project.getAmountRequested());
	    resp.setCreationDate(project.getCreationDate());
	    resp.setExpireDate(project.getExpireDate());
	    resp.setImageUrl(project.getImageUrl());
	    resp.setTags(project.getTags());
	    resp.setProjectId(project.getProjectId());
	    resp.setStatus(project.getStatus());
	    
	   
		return resp;
	}
	
    public Project fromRequestDTO(ProjectRequestDTO requestObj) {
		
    	if(requestObj == null) return null;
    	
        Project project = new Project();
        
		if(requestObj.getTags() != null) {
			Optional<String> resp = requestObj.getTags().stream().reduce((acc,val)-> acc+","+val);
			String tags = resp.isPresent() ? resp.get() : "";
			project.setTags(tags);
		}else project.setTags("");
		
		
		project.setStatus(requestObj.isStartGettingFunded() ? ProjectStatus.OPEN : ProjectStatus.PENDING);
		project.setAmountRequested(requestObj.getAmountRequested());
		project.setDescription(requestObj.getDescription());
		project.setTitle(requestObj.getTitle());
		project.setAmountCollected(0L);
        project.setCreationDate(DateHelper.convertDateToSQLDate(LocalDate.now().toString()));
		project.setExpireDate(DateHelper.convertDateToSQLDate(requestObj.getExpireDate()));
		project.setTagLine(requestObj.getTagline());
		project.setImageUrl(requestObj.getImageUrl());
	    
		return project;
	}
}

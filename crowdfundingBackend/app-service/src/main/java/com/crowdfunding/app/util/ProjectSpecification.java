package com.crowdfunding.app.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.crowdfunding.app.dto.SearchCriteria;
import com.crowdfunding.app.entity.Project;
import com.crowdfunding.common.enums.ProjectStatus;

public class ProjectSpecification implements Specification<Project> {

	private static final long serialVersionUID = -7232745468234862659L;
	private SearchCriteria criteria;

    public ProjectSpecification(SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}



	@Override
    public Predicate toPredicate
      (Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
 
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
              root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } 
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
              root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } 
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                
            	return builder.like(builder.lower(root.<String>get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");
            	
            } else if(root.get(criteria.getKey()).getJavaType() == ProjectStatus.class){
            	ProjectStatus status;
            	try {
            		status = ProjectStatus.valueOf(criteria.getValue().toString());
            	}catch(IllegalArgumentException e) {
            		status = ProjectStatus.NONE;	
            	}
            	
            	return builder.equal(root.get(criteria.getKey()), status);
            }else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}

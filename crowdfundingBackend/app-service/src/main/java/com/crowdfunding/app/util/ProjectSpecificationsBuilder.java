package com.crowdfunding.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import com.crowdfunding.app.dto.SearchCriteria;
import com.crowdfunding.app.entity.Project;

public class ProjectSpecificationsBuilder {
    
    private final List<SearchCriteria> params;

    public ProjectSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }
    
    public ProjectSpecificationsBuilder(List<SearchCriteria> params) {
		super();
		this.params = params;
	}

	public ProjectSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Project> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
          .map(ProjectSpecification::new)
          .collect(Collectors.toList());
        
        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result)
                  .and(specs.get(i));
        }       
        return result;
    }
}
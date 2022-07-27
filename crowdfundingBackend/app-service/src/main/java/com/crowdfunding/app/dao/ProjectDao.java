package com.crowdfunding.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crowdfunding.app.entity.Project;

@Repository
public interface ProjectDao extends JpaRepository<Project,Long> , JpaSpecificationExecutor<Project>{

	@Query(value = "select * from user_owned_projects where user_id = ?1", nativeQuery = true)
    Optional<List<Project>> findUserOwnedProjects(Long userId);
}

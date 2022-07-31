package com.crowdfunding.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crowdfunding.app.entity.User;

@Repository
public interface UserDao extends JpaRepository<User,Long>{

	
	@Query(value = "select * from user_info where email = ?1", nativeQuery = true)
    Optional<User> findUserUsingEmail(String userEmail);
	
	@Query(value = "select project_id from users_funded_projects where user_id = ?1", nativeQuery = true)
    Optional<List<Long>> findUserFundedProjectIds(Long userId);
	
	@Query(value = "select project_id from user_owned_projects where user_id = ?1", nativeQuery = true)
    Optional<List<Long>> findUserOwnedProjectIds(Long userId);
	
}

package com.crowdfunding.auth.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crowdfunding.auth.entity.UserAuth;

@Repository
public interface AuthDao extends JpaRepository<UserAuth,Long>{

	
	@Query(value = "select * from user_auth where email = ?1", nativeQuery = true)
    Optional<UserAuth> findAuthUsingEmail(String userEmail);
}

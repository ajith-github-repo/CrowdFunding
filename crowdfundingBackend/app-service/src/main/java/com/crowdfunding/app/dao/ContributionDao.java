package com.crowdfunding.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crowdfunding.app.entity.Contribution;

@Repository
public interface ContributionDao extends JpaRepository<Contribution, Long>{

}

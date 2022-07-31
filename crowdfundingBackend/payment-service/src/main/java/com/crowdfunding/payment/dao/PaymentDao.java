package com.crowdfunding.payment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crowdfunding.payment.entity.Payment;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Long>{

}

package com.crowdfunding.payment.util;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.crowdfunding.common.dto.PaymentRequestDTO;
import com.crowdfunding.common.util.DateHelper;
import com.crowdfunding.payment.entity.Payment;

@Component
public class PaymentMapper {

	
  public Payment fromRequestDTO(PaymentRequestDTO paymentReqObj) {
		
	  if(paymentReqObj == null) return null; 
	  
	  Payment payment = new Payment();
		
	  payment.setAmount(paymentReqObj.getAmount());
	  payment.setPayerId(paymentReqObj.getPayerId());
	  payment.setPayeeId(paymentReqObj.getPayeeId());
	  payment.setPaymentDate(DateHelper.convertDateToSQLDate(LocalDate.now().toString()));
	  
	  
	  return payment;
	}
}



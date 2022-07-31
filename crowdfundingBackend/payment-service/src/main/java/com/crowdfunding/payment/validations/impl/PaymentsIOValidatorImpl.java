package com.crowdfunding.payment.validations.impl;

import org.springframework.stereotype.Component;

import com.crowdfunding.common.dto.PaymentRequestDTO;
import com.crowdfunding.common.exceptions.DataValidationException;
import com.crowdfunding.payment.validations.IPaymentsIOValidator;

@Component
public class PaymentsIOValidatorImpl implements IPaymentsIOValidator{

	public boolean validate(PaymentRequestDTO payment) {
		if(payment.getAmount() == null || payment.getAmount() <= 0L)
			throw new DataValidationException("Amount cannot be less than zero");
		
		if(payment.getPayerId() == null)
			throw new DataValidationException("Payer ID is Missing");
		
		if(payment.getPayeeId() == null)
			throw new DataValidationException("Payee ID missing from contribution");
		
		return true;
	}
}

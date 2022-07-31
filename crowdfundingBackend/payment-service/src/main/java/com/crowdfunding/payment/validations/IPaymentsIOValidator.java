package com.crowdfunding.payment.validations;

import com.crowdfunding.common.dto.PaymentRequestDTO;

public interface IPaymentsIOValidator {

public boolean validate(PaymentRequestDTO payment);
}

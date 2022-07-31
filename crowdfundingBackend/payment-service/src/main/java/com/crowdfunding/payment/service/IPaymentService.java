package com.crowdfunding.payment.service;

import com.crowdfunding.common.dto.ContributionResponseDTO;
import com.crowdfunding.common.dto.PaymentRequestDTO;

public interface IPaymentService {

	public ContributionResponseDTO pay(String tokenHeader,PaymentRequestDTO paymentIO);
}

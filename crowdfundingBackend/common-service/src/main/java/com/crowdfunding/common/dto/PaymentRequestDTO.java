package com.crowdfunding.common.dto;

public class PaymentRequestDTO{

	private Long amount;
	private Long payerId;
	private Long payeeId;
	
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getPayerId() {
		return payerId;
	}

	public void setPayerId(Long payerId) {
		this.payerId = payerId;
	}

	public Long getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(Long payeeId) {
		this.payeeId = payeeId;
	}

	public PaymentRequestDTO(Long amount, Long payerId, Long payeeId) {
		super();
		this.amount = amount;
		this.payerId = payerId;
		this.payeeId = payeeId;
	}
	
	public PaymentRequestDTO() {

	}
}

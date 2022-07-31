package com.crowdfunding.payment.entity;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.crowdfunding.payment.enums.PaymentMethod;

@Entity
@Table(name="payment_info")
public class Payment {

	@Id
	@Column(name="payment_id")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long paymentId;
	
	@Column(name="amount")
	private Long amount;
	
	@Column(name="payment_date")
	private Date paymentDate;
	
	@Column(name="payer_id")
    private Long payerId;

	@Column(name="payee_id")
    private Long payeeId;
	
	@Column(name="payment_method")
	private PaymentMethod paymentMethod;

	public Payment(Long paymentId, Long amount, Date paymentDate, Long payerId, Long payeeId, PaymentMethod paymentMethod) {
		super();
		this.paymentId = paymentId;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.payerId = payerId;
		this.payeeId = payeeId;
		this.paymentMethod = paymentMethod;
	}
	
	public Payment() {
		super();
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, payeeId, payerId, paymentDate, paymentId, paymentMethod);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(payeeId, other.payeeId)
				&& Objects.equals(payerId, other.payerId) && Objects.equals(paymentDate, other.paymentDate)
				&& Objects.equals(paymentId, other.paymentId) && Objects.equals(paymentMethod, other.paymentMethod);
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
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

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
}

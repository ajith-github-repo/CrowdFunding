package com.crowdfunding.payment.enums;

public enum PaymentMethod {	
	CARD("CARD"),
	UPI("UPI"),
	NET_BANKING("NET_BANKING"),
	WALLET("WALLET");
	
	private String label;

    private PaymentMethod(String label) {

    }
    public String getLabel() {
        return label;
    }
}

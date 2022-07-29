package com.crowdfunding.common.enums;

public enum ProjectStatus {	
	PENDING("pending"),
	OPEN("open"),
	ARCHIVED("archived"),
	CLOSED("closed"),
	NONE("none");
	private String label;

    private ProjectStatus(String label) {

    }
    public String getLabel() {
        return label;
    }
}

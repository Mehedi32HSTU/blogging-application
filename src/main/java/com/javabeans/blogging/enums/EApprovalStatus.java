package com.javabeans.blogging.enums;

public enum EApprovalStatus {

	APPROVED("APPROVED"),
	PENDING("PENDING");

	private final String getStatus;

	private EApprovalStatus(String getStatus) {
		this.getStatus = getStatus;
	}

	public String getGetStatus() {
		return getStatus;
	}
}

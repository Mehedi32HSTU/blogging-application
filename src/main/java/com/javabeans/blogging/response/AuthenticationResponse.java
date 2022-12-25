package com.javabeans.blogging.response;

import java.util.ArrayList;
import java.util.List;

import com.javabeans.blogging.enums.EApprovalStatus;

public class AuthenticationResponse {
	private Long userId;
	private String fullName;
	private String username;
	private String token;
	private EApprovalStatus approvalStatus;
	private boolean isActive;
	private List<String> roles = new ArrayList<>();

	public AuthenticationResponse() {

	}

	public AuthenticationResponse(Long userId, String fullName, String username, String token,
			EApprovalStatus approvalStatus, boolean isActive, List<String> roles) {
		this.userId = userId;
		this.fullName = fullName;
		this.username = username;
		this.token = token;
		this.approvalStatus = approvalStatus;
		this.isActive = isActive;
		this.roles = roles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public EApprovalStatus getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(EApprovalStatus approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}

package com.javabeans.blogging.users;

import org.springframework.http.ResponseEntity;

import com.javabeans.blogging.enums.EApprovalStatus;

public interface UserInformationService {

	public ResponseEntity<?>addNewUser(UserInformation userInformation);
	public ResponseEntity<?>getUserById(Long userId);
	public ResponseEntity<?>getUserByUsername(String username);
	public ResponseEntity<?>getAllUsers();
	public ResponseEntity<?>getAllUsersByApprovalStatus(EApprovalStatus approvalStatus);
	public ResponseEntity<?>getAllUsersByActivity(boolean isActive);
	public ResponseEntity<?>changeUserActivity(Long userId, boolean isActive);
	public ResponseEntity<?>performOnUserRequest(Long userId, EApprovalStatus approvalStatus);

}

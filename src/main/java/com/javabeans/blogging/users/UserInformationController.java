package com.javabeans.blogging.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javabeans.blogging.enums.EApprovalStatus;

@RestController
@RequestMapping("/user-info")
public class UserInformationController {

	@Autowired
	private UserInformationService userInformationService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> addNewUser( @RequestBody UserInformation userInformation){
		return userInformationService.addNewUser(userInformation);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllUsers(){
		return userInformationService.getAllUsers();
	}

	@RequestMapping(value = "/{userId}/get", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN') or @securityService.hasEntry(#userId)")
	public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId){
		return userInformationService.getUserById(userId);
	}

	@RequestMapping(value = "/byUsername", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN') or @securityService.hasEntry(#username)")
	public ResponseEntity<?> getUserByUsername(@RequestParam(name = "username", required = true) String username){
		return userInformationService.getUserByUsername(username);
	}

	@RequestMapping(value = "/byApproval", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllUsersByApprovalStatus(@RequestParam(name = "approval-status", required = true) EApprovalStatus approvalStatus){
		return userInformationService.getAllUsersByApprovalStatus(approvalStatus);
	}

	@RequestMapping(value = "/byActivity", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllUsersByActivity(@RequestParam(name = "isActive", required = true) boolean isActive){
		return userInformationService.getAllUsersByActivity(isActive);
	}

	@RequestMapping(value = "/{userId}/change-activity", method = RequestMethod.PATCH)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> changeUserActivity(
			@PathVariable("userId") Long userId,
			@RequestParam(name = "isActive", required = true) boolean isActive){
		return userInformationService.changeUserActivity(userId, isActive);
	}

	@RequestMapping(value = "/{userId}/change-approval", method = RequestMethod.PATCH)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> performOnUserRequest(
			@PathVariable("userId") Long userId,
			@RequestParam(name = "approval-status", required = true) EApprovalStatus approvalStatus){
		return userInformationService.performOnUserRequest(userId, approvalStatus);
	}
}

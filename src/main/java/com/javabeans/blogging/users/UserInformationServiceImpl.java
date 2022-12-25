package com.javabeans.blogging.users;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javabeans.blogging.enums.EApprovalStatus;
import com.javabeans.blogging.enums.ERole;
import com.javabeans.blogging.response.MessageResponse;
import com.javabeans.blogging.roles.Roles;
import com.javabeans.blogging.security.SecurityService;
import com.javabeans.blogging.util.UserInformationUtil;

@Service
public class UserInformationServiceImpl implements UserInformationService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserInformationRepository userInformationRepository;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<?> addNewUser(UserInformation userInformationReq) {
		try {
			logger.info("<<<<<---------- addNewUser method is called ---------->>>>>");

			UserInformation loggedUser = securityService.getAuthorizedUser();
			if(Objects.nonNull(loggedUser) && UserInformationUtil.hasDesiredRole(loggedUser, ERole.ROLE_ADMIN))
				userInformationReq.setApprovalStatus(EApprovalStatus.APPROVED);
			else
				userInformationReq.setApprovalStatus(EApprovalStatus.PENDING);
			if(userInformationReq.getRoles().size() < 1){
				Roles role = new Roles();
				role.setId(2L);
				userInformationReq.getRoles().add( role);
			}
			userInformationReq.setPassword(passwordEncoder.encode(userInformationReq.getPassword()));
			userInformationReq.setActive(true);
			UserInformation userInformation = userInformationRepository.save(userInformationReq);
			return ResponseEntity.ok().body(userInformation);
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in addNewUser method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> getUserById(Long userId) {
		try {
			logger.info("<<<<<---------- getUserById method is called ---------->>>>>");
			UserInformation userInformation = userInformationRepository.findByUserId(userId);
			if(Objects.isNull(userInformation))
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new MessageResponse("UserInformation Not Found"));

			return ResponseEntity.ok().body(userInformation);
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in getUserById method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> getUserByUsername(String username) {
		try {
			logger.info("<<<<<---------- getUserByUsername method is called ---------->>>>>");
			if(username.trim().isEmpty())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("Username Can't be Empty."));

			UserInformation userInformation = userInformationRepository.findByUsername(username);
			if(Objects.isNull(userInformation))
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new MessageResponse("UserInformation Not Found"));

			return ResponseEntity.ok().body(userInformation);
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in getUserByUsername method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> getAllUsers() {
		try {
			logger.info("<<<<<---------- getAllUsers method is called ---------->>>>>");

			return ResponseEntity.ok().body(userInformationRepository.findAll());
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in getAllUsers method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> getAllUsersByApprovalStatus(EApprovalStatus approvalStatus) {
		try {
			logger.info("<<<<<---------- getAllUsersByApprovalStatus method is called ---------->>>>>");

			return ResponseEntity.ok().body(userInformationRepository.findByApprovalStatusOrderByUserId(approvalStatus));
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in getAllUsersByApprovalStatus method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> getAllUsersByActivity(boolean isActive) {
		try {
			logger.info("<<<<<---------- getAllUsersByActivity method is called ---------->>>>>");

			return ResponseEntity.ok().body(userInformationRepository.findByIsActiveOrderByUserId(isActive));
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in getAllUsersByActivity method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> changeUserActivity(Long userId, boolean isActive) {
		try {
			logger.info("<<<<<---------- performActionOnUserActivity method is called ---------->>>>>");
			UserInformation userInformation = userInformationRepository.findByUserId(userId);
			if(Objects.isNull(userInformation))
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new MessageResponse("UserInformation Not Found"));
			userInformation.setActive(isActive);

			return ResponseEntity.ok().body(userInformationRepository.save(userInformation));
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in performActionOnUserActivity method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> performOnUserRequest(Long userId, EApprovalStatus approvalStatus) {
		try {
			logger.info("<<<<<---------- performOnUserRequest method is called ---------->>>>>");
			UserInformation userInformation = userInformationRepository.findByUserId(userId);
			if(Objects.isNull(userInformation))
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new MessageResponse("UserInformation Not Found"));
			userInformation.setApprovalStatus(approvalStatus);

			return ResponseEntity.ok().body(userInformationRepository.save(userInformation));
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in performOnUserRequest method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

}

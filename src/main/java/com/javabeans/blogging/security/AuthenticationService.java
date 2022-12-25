package com.javabeans.blogging.security;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javabeans.blogging.enums.EApprovalStatus;
import com.javabeans.blogging.response.AuthenticationRequest;
import com.javabeans.blogging.response.AuthenticationResponse;
import com.javabeans.blogging.response.MessageResponse;
import com.javabeans.blogging.roles.RolesRepository;
import com.javabeans.blogging.security.jwt.JwtUtils;
import com.javabeans.blogging.users.UserInformation;
import com.javabeans.blogging.users.UserInformationRepository;

@Service
public class AuthenticationService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserInformationRepository userInformationRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

	public ResponseEntity<?> authenticateUser(AuthenticationRequest authenticationRequest) {
		logger.info("<<<<<----------- authenticateUser is called ----------->>>>>");

		String username = authenticationRequest.getUsername();
		UserInformation userInformation = userInformationRepository.findByUsername(username);
		
		if(Objects.isNull(userInformation)) {
			logger.info("User : " + username + " Not Registered");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User Not Registered"));
		}
		else if(!userInformation.getApprovalStatus().equals(EApprovalStatus.APPROVED)) {
			logger.info("User : " + username + " Account Not Approved Yet.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User Account Not Approved Yet."));
		}
		else if(!userInformation.isActive()) {
			logger.info("User : " + username + " Inactive.");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new MessageResponse("Inactive User"));
		}
		
		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch(BadCredentialsException ex) {
			logger.info("Bad credentials exception occurred.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Bad credentials exception occurred."));
		}

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AuthenticationResponse(
                userDetails.getUserId(),
                userDetails.getFullName(),
                userDetails.getUsername(),
                jwtToken,
                userDetails.getApprovalStatus(),
                userDetails.isActive(),
                roles));
	}
}

package com.javabeans.blogging;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.javabeans.blogging.enums.EApprovalStatus;
import com.javabeans.blogging.enums.ERole;
import com.javabeans.blogging.roles.Roles;
import com.javabeans.blogging.roles.RolesRepository;
import com.javabeans.blogging.users.UserInformation;
import com.javabeans.blogging.users.UserInformationRepository;

@Component
public class InitiateData implements CommandLineRunner{

	@Autowired
	private UserInformationRepository userInformationRepository;

	@Autowired
	private RolesRepository rolesRepository;

	@Override
	public void run(String... args) throws Exception {
		if(!(rolesRepository.findAll().size() > 0)) {
			Roles adminRole = new Roles();
			adminRole.setName(ERole.ROLE_ADMIN);

			Roles userRole = new Roles();
			userRole.setName(ERole.ROLE_USER);

			Roles savedAdminRole = rolesRepository.save(adminRole);
			rolesRepository.save(userRole);

			UserInformation userInformation = new UserInformation();
			userInformation.setFullName("Default Admin");
			userInformation.setUsername("default_admin");
			userInformation.setActive(true);
			userInformation.setApprovalStatus(EApprovalStatus.APPROVED);
			userInformation.setPassword("admin_1234");
			userInformation.setRoles(Arrays.asList(savedAdminRole));

			userInformationRepository.save(userInformation);
		}
	}

}

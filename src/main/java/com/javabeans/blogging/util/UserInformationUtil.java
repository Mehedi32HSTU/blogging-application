package com.javabeans.blogging.util;

import java.util.List;

import com.javabeans.blogging.enums.ERole;
import com.javabeans.blogging.roles.Roles;
import com.javabeans.blogging.users.UserInformation;

public class UserInformationUtil {

	public static boolean hasDesiredRole(UserInformation userInformation, ERole desiredRole) {
		List<Roles> roles = userInformation.getRoles();
		for(Roles role : roles) {
			if(role.getName().equals(desiredRole))
				return true;
		}
		return false;
	}

}

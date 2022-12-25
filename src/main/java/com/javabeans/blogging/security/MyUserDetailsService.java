package com.javabeans.blogging.security;

import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.javabeans.blogging.users.UserInformation;
import com.javabeans.blogging.users.UserInformationRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	private UserInformationRepository userInformationRepository;

	@Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInformation userInformation = userInformationRepository.findByUsername(username);

        if(Objects.isNull(userInformation)) throw new UsernameNotFoundException("Username Not Found");

        return UserDetailsImpl.build(userInformation);
    }

}

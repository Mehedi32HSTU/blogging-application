package com.javabeans.blogging.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javabeans.blogging.enums.EApprovalStatus;
import com.javabeans.blogging.users.UserInformation;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long userId;
	private String fullName;
	private String username;
	private EApprovalStatus approvalStatus;
	private boolean isActive;
	@JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long userId, String fullName, String username, EApprovalStatus approvalStatus,
			boolean isActive, String password, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.username = username;
		this.approvalStatus = approvalStatus;
		this.isActive = isActive;
		this.authorities = authorities;
		this.password = password;
	}
	public static UserDetailsImpl build(UserInformation userInformation) {
        List<GrantedAuthority> authorities = userInformation.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
        		userInformation.getUserId(),
        		userInformation.getFullName(),
        		userInformation.getUsername(),
        		userInformation.getApprovalStatus(),
        		userInformation.isActive(),
        		userInformation.getPassword(),
                authorities);
    }
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
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
	public void setUsername(String username) {
		this.username = username;
	}

}

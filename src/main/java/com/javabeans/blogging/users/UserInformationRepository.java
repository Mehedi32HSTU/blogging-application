package com.javabeans.blogging.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javabeans.blogging.enums.EApprovalStatus;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Long>{
	UserInformation findByUserId(Long userId);
	UserInformation findByUsername(String username);
	List<UserInformation> findByApprovalStatusOrderByUserId(EApprovalStatus approvalStatus);
	List<UserInformation> findByIsActiveOrderByUserId(boolean isActive);
	boolean existsByUserId(Long userId);

}

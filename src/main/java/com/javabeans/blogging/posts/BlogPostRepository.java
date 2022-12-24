package com.javabeans.blogging.posts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javabeans.blogging.enums.EApprovalStatus;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long>{

	List<BlogPost> findByApprovalStatusOrderByIdAsc(EApprovalStatus approvalStatus);
	
	@Query(value = "select blog_post.* from blog_post where blog_post.post_creator_id = ?1 order by blog_post.id asc", nativeQuery = true)
	List<BlogPost> findByPostCreatorId(Long postCreatorId);
	

}

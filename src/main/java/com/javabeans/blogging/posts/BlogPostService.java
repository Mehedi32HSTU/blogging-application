package com.javabeans.blogging.posts;

import org.springframework.http.ResponseEntity;

import com.javabeans.blogging.enums.EApprovalStatus;
import com.javabeans.blogging.enums.EReaction;


public interface BlogPostService {
	public ResponseEntity<?> createNewBlogPost(BlogPost blogPostReq);
	public ResponseEntity<?> getBlogPostById(Long blogPostId);
	public ResponseEntity<?> getAllBlogPost();
	public ResponseEntity<?> getAllBlogPostByStatus(EApprovalStatus approvalStatus); // ** Have to handle case that, if pending then only admin can see them.
	public ResponseEntity<?> getAllBlogPostByCreatorId(Long postCreatorId);
	public ResponseEntity<?> approveBlogPostById(Long blogPostId);
	public ResponseEntity<?> deleteBlogPostByPostId(Long blogPostId);
	public ResponseEntity<?> giveReactionToBlogPost(Long blogPostId, EReaction reaction);
	public ResponseEntity<?> removeReactionFromBlogPost(Long reactionId);
	

}

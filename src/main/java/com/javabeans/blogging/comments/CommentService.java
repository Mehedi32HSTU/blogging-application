package com.javabeans.blogging.comments;

import org.springframework.http.ResponseEntity;

public interface CommentService {
	public ResponseEntity<?> createNewComment(Long blogPostId, Comment commentReq);
	public ResponseEntity<?> getCommentById(Long commentId);
	public ResponseEntity<?> getAllComments();
	public ResponseEntity<?> getAllCommentsOfPost(Long blogPostId);
	public ResponseEntity<?> deleteComment(Long commentId);

}

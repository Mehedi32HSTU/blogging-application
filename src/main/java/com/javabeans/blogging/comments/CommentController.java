package com.javabeans.blogging.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@RequestMapping(value = "/blog-post/{blogPostId}/add", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN') or @securityService.isUserApprovedActive()")
	public ResponseEntity<?> createNewComment(@PathVariable("blogPostId") Long blogPostId,
			@RequestBody Comment comment){

		return commentService.createNewComment(blogPostId, comment);
	}

	@RequestMapping(value = "/{commentId}/get", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN') or @securityService.isUserApprovedActive()")
	public ResponseEntity<?> getCommentById(@PathVariable("commentId") Long commentId){

		return commentService.getCommentById(commentId);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN') or @securityService.isUserApprovedActive()")
	public ResponseEntity<?> getAllComments(){

		return commentService.getAllComments();
	}

	@RequestMapping(value = "/blog-post/{blogPostId}/all", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN') or @securityService.isUserApprovedActive()")
	public ResponseEntity<?> getAllCommentsOfPost(@PathVariable("blogPostId") Long blogPostId){
		
		return commentService.getAllCommentsOfPost(blogPostId);
	}

	@RequestMapping(value = "/{commentId}/delete", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ADMIN') or @securityService.isContentOwner('post-comment', #commentId)")
	public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId){
		
		return commentService.deleteComment(commentId);
	}

}

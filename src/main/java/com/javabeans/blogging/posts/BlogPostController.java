package com.javabeans.blogging.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javabeans.blogging.enums.EApprovalStatus;
import com.javabeans.blogging.enums.EReaction;

@RestController
@RequestMapping("/blog-post")
public class BlogPostController {

	@Autowired
	private BlogPostService blogPostService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN') or @securityService.isUserApprovedActive()")
	public ResponseEntity<?> createNewBlogPost( @RequestBody BlogPost blogPost){

		return blogPostService.createNewBlogPost(blogPost);
	}

	@RequestMapping(value = "/{blogPostId}/get", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN') or @securityService.isUserApprovedActive()")
	public ResponseEntity<?> getBlogPostById(@PathVariable("blogPostId") Long blogPostId){

		return blogPostService.getBlogPostById(blogPostId);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN') or @securityService.isUserApprovedActive()")
	public ResponseEntity<?> getAllBlogPost(){

		return blogPostService.getAllBlogPost();
	}

	@RequestMapping(value = "/byStatus", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN') or @securityService.isUserApprovedActive()")
	public ResponseEntity<?> getAllBlogPostByStatus(@RequestParam(name = "approval-status",
		defaultValue = "APPROVED") EApprovalStatus approvalStatus) {

		return blogPostService.getAllBlogPostByStatus(approvalStatus);
	}

	@RequestMapping(value = "/creator/{postCreatorId}/get", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN') or @securityService.isUserApprovedActive()")
	public ResponseEntity<?> getAllBlogPostByCreatorId(@PathVariable("postCreatorId") Long postCreatorId) {

		return blogPostService.getAllBlogPostByCreatorId(postCreatorId);
	}

	@RequestMapping(value = "/{blogPostId}/approve", method = RequestMethod.PATCH)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> approveBlogPostById(@PathVariable("blogPostId") Long blogPostId) {

		return blogPostService.approveBlogPostById(blogPostId);
	}

	@RequestMapping(value = "/{blogPostId}/delete", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ADMIN') or @securityService.isContentOwner('blog-post', #blogPostId)")
	public ResponseEntity<?> deleteBlogPostByPostId(@PathVariable("blogPostId") Long blogPostId) {
		
		return blogPostService.deleteBlogPostByPostId(blogPostId);
	}

	@RequestMapping(value = "/{blogPostId}/react", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN') or @securityService.isUserApprovedActive()")
	public ResponseEntity<?> giveReactionToBlogPost(@PathVariable("blogPostId") Long blogPostId,
			@RequestParam(name = "reaction", defaultValue = "LIKE") EReaction reaction) {

		return blogPostService.giveReactionToBlogPost(blogPostId, reaction);
	}

	@RequestMapping(value = "/reaction/{reactionId}/remove", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ADMIN') or @securityService.isContentOwner('reaction', #reactionId)")
	public ResponseEntity<?> removeReactionFromBlogPost(@PathVariable("reactionId") Long reactionId) {

		return blogPostService.removeReactionFromBlogPost(reactionId);
	}

}

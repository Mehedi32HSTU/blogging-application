package com.javabeans.blogging.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> createNewBlogPost( @RequestBody BlogPost blogPost){

		return blogPostService.createNewBlogPost(blogPost);
	}
	
	@RequestMapping(value = "/{blogPostId}/get", method = RequestMethod.GET)
	public ResponseEntity<?> getBlogPostById(@PathVariable("blogPostId") Long blogPostId){

		return blogPostService.getBlogPostById(blogPostId);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> getAllBlogPost(){

		return blogPostService.getAllBlogPost();
	}

	@RequestMapping(value = "/byStatus", method = RequestMethod.GET)
	public ResponseEntity<?> getAllBlogPostByStatus(@RequestParam(name = "approval-status",
		defaultValue = "APPROVED") EApprovalStatus approvalStatus) {

		return blogPostService.getAllBlogPostByStatus(approvalStatus);
	}

	@RequestMapping(value = "/creator/{postCreatorId}/get", method = RequestMethod.GET)
	public ResponseEntity<?> getAllBlogPostByCreatorId(@PathVariable("postCreatorId") Long postCreatorId) {

		return blogPostService.getAllBlogPostByCreatorId(postCreatorId);
	}

	@RequestMapping(value = "/{blogPostId}/approve", method = RequestMethod.PATCH)
	public ResponseEntity<?> approveBlogPostById(@PathVariable("blogPostId") Long blogPostId) {

		return blogPostService.approveBlogPostById(blogPostId);
	}

	@RequestMapping(value = "/{blogPostId}/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBlogPostByPostId(@PathVariable("blogPostId") Long blogPostId) {
		
		return blogPostService.deleteBlogPostByPostId(blogPostId);
	}

	@RequestMapping(value = "/{blogPostId}/react", method = RequestMethod.POST)
	public ResponseEntity<?> giveReactionToBlogPost(@PathVariable("blogPostId") Long blogPostId,
			@RequestParam(name = "reaction", defaultValue = "LIKE") EReaction reaction) {

		return blogPostService.giveReactionToBlogPost(blogPostId, reaction);
	}

	@RequestMapping(value = "/reaction/{reactionId}/remove", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeReactionFromBlogPost(@PathVariable("reactionId") Long reactionId) {
		
		return blogPostService.removeReactionFromBlogPost(reactionId);
	}

}

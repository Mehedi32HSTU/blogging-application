package com.javabeans.blogging.posts;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.javabeans.blogging.enums.EApprovalStatus;
import com.javabeans.blogging.enums.EReaction;
import com.javabeans.blogging.response.MessageResponse;
import com.javabeans.blogging.users.UserInformation;
import com.javabeans.blogging.users.UserInformationRepository;

@Service
public class BlogPostServiceImpl implements BlogPostService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserInformationRepository userInformationRepository;

	@Autowired
	private BlogPostRepository blogPostRepository;

	@Autowired
	private PostReactionRepository postReactionRepository;

	@Override
	public ResponseEntity<?> createNewBlogPost(BlogPost blogPostReq) {
		try {
			logger.info("<<<<<---------- createNewBlogPost method is called ---------->>>>>");
			/*
			 * TODO: Have to add validations
			 * have to get logged uder and set to blog post creator.
			 */
//			UserInformation loggedUser = getAuthorizedUser();
//			blogPostReq.setPostCreator(loggedUser);
			
			blogPostReq.setCreatedDate(new Date());
			blogPostReq.setApprovalStatus(EApprovalStatus.PENDING); // by default approved only if posted by admin.

			BlogPost savedBlogPost = blogPostRepository.save(blogPostReq);
			return ResponseEntity.ok().body(savedBlogPost);
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in createNewBlogPost method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> getBlogPostById(Long blogPostId) {
		try {
			logger.info("<<<<<---------- getBlogPostById method is called ---------->>>>>");
			Optional<BlogPost> blogPostOpt = blogPostRepository.findById(blogPostId);
			if(!blogPostOpt.isPresent())
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new MessageResponse("BlogPost Not Found"));

			return ResponseEntity.ok().body(blogPostOpt.get());
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in getBlogPostById method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> getAllBlogPost() {
		try {
			logger.info("<<<<<---------- getAllBlogPost method is called ---------->>>>>");

			return ResponseEntity.ok().body(blogPostRepository.findAll());
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in getAllBlogPost method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> getAllBlogPostByStatus(EApprovalStatus approvalStatus) {
		try {
			logger.info("<<<<<---------- getAllBlogPostByStatus method is called ---------->>>>>");
			/*
			 * TODO:
			 * Add validation so that without admin no user can see pending posts.
			 * Without admin, other users will be able to see only approved posts.
			 */

			return ResponseEntity.ok().body(blogPostRepository.findByApprovalStatusOrderByIdAsc(approvalStatus));
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in getAllBlogPostByStatus method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> getAllBlogPostByCreatorId(Long postCreatorId) {
		try {
			logger.info("<<<<<---------- getAllBlogPostByCreatorId method is called ---------->>>>>");

			if(!userInformationRepository.existsByUserId(postCreatorId))
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new MessageResponse("Post Creator Not Found"));

			return ResponseEntity.ok().body(blogPostRepository.findByPostCreatorId(postCreatorId));
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in getAllBlogPostByCreatorId method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> approveBlogPostById(Long blogPostId) {
		try {
			logger.info("<<<<<---------- approveBlogPostById method is called ---------->>>>>");
			Optional<BlogPost> blogPostOpt = blogPostRepository.findById(blogPostId);
			if(!blogPostOpt.isPresent())
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new MessageResponse("BlogPost Not Found"));

			BlogPost blogPost = blogPostOpt.get();
			blogPost.setApprovalStatus(EApprovalStatus.APPROVED);
			BlogPost savedBlogPost = blogPostRepository.save(blogPost);

			return ResponseEntity.ok().body(savedBlogPost);
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in approveBlogPostById method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> deleteBlogPostByPostId(Long blogPostId) {
		try {
			logger.info("<<<<<---------- deleteBlogPostByPostId method is called ---------->>>>>");
			Optional<BlogPost> blogPostOpt = blogPostRepository.findById(blogPostId);
			if(!blogPostOpt.isPresent())
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new MessageResponse("BlogPost Not Found"));
			BlogPost blogPost = blogPostOpt.get();

			/*
			 * TODO: Add validation so that:
			 * Creator can delete his/her own post.
			 * Admin can delete any ones post.
			 */
//			UserInformation loggedUser = getAuthorizedUser();
//			if(hasRoleAdmin(loggedUser) || (blogPost.getPostCreator().getUserId()).equals(loggedUser.getUserId())) {
//				blogPostRepository.delete(blogPost);
//			}
//			else {
//				return ResponseEntity.status(HttpStatus.FORBIDDEN)
//						.body(new MessageResponse("Not Allowed to Delete The Post."));
//			}

			blogPostRepository.delete(blogPost);  // Have to remove this when security is integrated.

			return ResponseEntity.ok().body(new MessageResponse("BlogPost Deleted Successfully."));
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in deleteBlogPostByPostId method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> giveReactionToBlogPost(Long blogPostId, EReaction reaction) {
		try {
			logger.info("<<<<<---------- giveReactionToBlogPost method is called ---------->>>>>");
			Optional<BlogPost> blogPostOpt = blogPostRepository.findById(blogPostId);
			if(!blogPostOpt.isPresent())
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new MessageResponse("BlogPost Not Found"));

			BlogPost blogPost = blogPostOpt.get();
			if(!blogPost.getApprovalStatus().equals(EApprovalStatus.APPROVED))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("BlogPost is Not Approved Yet."));
			PostReaction postReaction = new PostReaction();
			postReaction.setReaction(reaction);
			/*
			 * TODO: Add validation so that:
			 * 
			 */
//			UserInformation loggedUser = getAuthorizedUser();
//			postReaction.setReactorId(loggedUser.getUserId());
			postReaction.setReactorId(12L);

			PostReaction savedPostReaction = postReactionRepository.save(postReaction);
			blogPost.getPostReactions().add(savedPostReaction);

			BlogPost savedBlogPost = blogPostRepository.save(blogPost);

			return ResponseEntity.ok().body(savedBlogPost);
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in giveReactionToBlogPost method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> removeReactionFromBlogPost(Long reactionId) {
		try {
			logger.info("<<<<<---------- removeReactionFromBlogPost method is called ---------->>>>>");
			Optional<PostReaction> postReactionOpt = postReactionRepository.findById(reactionId);
			if(!postReactionOpt.isPresent())
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new MessageResponse("Reaction Not Found"));
			PostReaction postReaction = postReactionOpt.get();

			/*
			 * TODO: Add validation so that:
			 * Creator can delete his/her own post.
			 * Admin can delete any ones post.
			 */
//			UserInformation loggedUser = getAuthorizedUser();
//			if( postReaction.getReactorId().equals(loggedUser.getUserId())) {
//				postReactionRepository.delete(postReaction);
//			}
//			else {
//				return ResponseEntity.status(HttpStatus.FORBIDDEN)
//						.body(new MessageResponse("Not Allowed to Delete The Reaction."));
//			}

			postReactionRepository.delete(postReaction);

			return ResponseEntity.ok().body(new MessageResponse("PostReaction Deleted Successfully."));
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in removeReactionFromBlogPost method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

}

package com.javabeans.blogging.comments;

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
import com.javabeans.blogging.posts.BlogPost;
import com.javabeans.blogging.posts.BlogPostRepository;
import com.javabeans.blogging.response.MessageResponse;
import com.javabeans.blogging.users.UserInformation;
import com.javabeans.blogging.users.UserInformationRepository;

@Service
public class CommentServiceImpl implements CommentService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserInformationRepository userInformationRepository;

	@Autowired
	private BlogPostRepository blogPostRepository;

	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public ResponseEntity<?> createNewComment(Long blogPostId, Comment commentReq) {
		try {
			logger.info("<<<<<---------- createNewComment method is called ---------->>>>>");
			/*
			 * TODO: Have to add validations
			 */
			Optional<BlogPost> blogPostOpt = blogPostRepository.findById(blogPostId);
			if(blogPostOpt.isPresent()) {
				BlogPost blogPost = blogPostOpt.get();
				if(blogPost.getApprovalStatus().equals(EApprovalStatus.APPROVED)) {
					List<Comment> allComments = blogPost.getComments();
					/*
					 * TODO: Have to add validations
					 * have to get logged uder and set to blog post creator.
					 */
//					UserInformation loggedUser = getAuthorizedUser();
//					commentReq.setCommentCreator(loggedUser);
					commentReq.setCreatedDate(new Date());
					Comment savedComment = commentRepository.save(commentReq);
					allComments.add(savedComment);
					blogPost.setComments(allComments);
					BlogPost savedBlogPost = blogPostRepository.save(blogPost);
					return ResponseEntity.ok().body(savedBlogPost);
				}
				else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new MessageResponse("BlogPost is Not Approved Yet."));
				}
			}
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new MessageResponse("BlogPost Not Found"));
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in createNewComment method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> getCommentById(Long commentId) {
		try {
			logger.info("<<<<<---------- getCommentById method is called ---------->>>>>");
			Optional<Comment> commentOpt = commentRepository.findById(commentId);
			if(!commentOpt.isPresent())
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new MessageResponse("Comment Not Found"));

			return ResponseEntity.ok().body(commentOpt.get());
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in getCommentById method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> getAllComments() {
		try {
			logger.info("<<<<<---------- getAllComments method is called ---------->>>>>");

			return ResponseEntity.ok().body(commentRepository.findAll());
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in getAllComments method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> getAllCommentsOfPost(Long blogPostId) {
		try {
			logger.info("<<<<<---------- getAllCommentsOfPost method is called ---------->>>>>");

			Optional<BlogPost> blogPostOpt = blogPostRepository.findById(blogPostId);
			if(blogPostOpt.isPresent()) {
				BlogPost blogPost = blogPostOpt.get();
				return ResponseEntity.ok().body(blogPost.getComments());
			}
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new MessageResponse("BlogPost Not Found"));
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in getAllCommentsOfPost method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

	@Override
	public ResponseEntity<?> deleteComment(Long commentId) {
		try {
			logger.info("<<<<<---------- deleteComment method is called ---------->>>>>");
			Optional<Comment> commentOpt = commentRepository.findById(commentId);
			if(!commentOpt.isPresent())
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new MessageResponse("Comment Not Found"));
			Comment comment = commentOpt.get();

			/*
			 * TODO: Add validation so that:
			 * Creator can delete his/her own post.
			 * Admin can delete any ones post.
			 */
//			UserInformation loggedUser = getAuthorizedUser();
//			if(hasRoleAdmin(loggedUser) || (comment.getCommentCreator().getUserId()).equals(loggedUser.getUserId())) {
//				commentRepository.delete(comment);
//			}
//			else {
//				return ResponseEntity.status(HttpStatus.FORBIDDEN)
//						.body(new MessageResponse("Not Allowed to Delete The Comment."));
//			}

			commentRepository.delete(comment);  // Have to remove this when security is integrated.

			return ResponseEntity.ok().body(new MessageResponse("Comment Deleted Successfully."));
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in deleteComment method. ---------->>>>>", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Exception "+e.getMessage()+" has occured."));
		}
	}

}

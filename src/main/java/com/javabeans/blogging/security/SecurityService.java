package com.javabeans.blogging.security;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.javabeans.blogging.comments.Comment;
import com.javabeans.blogging.comments.CommentRepository;
import com.javabeans.blogging.enums.EApprovalStatus;
import com.javabeans.blogging.posts.BlogPost;
import com.javabeans.blogging.posts.BlogPostRepository;
import com.javabeans.blogging.posts.PostReaction;
import com.javabeans.blogging.posts.PostReactionRepository;
import com.javabeans.blogging.users.UserInformation;
import com.javabeans.blogging.users.UserInformationRepository;

@Service
public class SecurityService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserInformationRepository userInformationRepository;

	@Autowired
	private BlogPostRepository blogPostRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostReactionRepository postReactionRepository;

	public boolean hasEntry(Long userId) {
		try {
			if (Objects.isNull(userId)) return false;
			UserInformation userInformation = getAuthorizedUser();
			if(Objects.isNull(userInformation))
				return false;
			Optional<UserInformation> requestedUserOpt = userInformationRepository.findById(userId);
			if(requestedUserOpt.isPresent()) 
				return requestedUserOpt.get().getUsername().equals(userInformation.getUsername());
			return false;
			
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in hasEntry method. ---------->>>>>", e.getMessage());
			return false;
		}
	}

	public boolean hasEntry(String username) {
		try {
			if (Objects.isNull(username) || username.isEmpty()) return false;
			UserInformation userInformation = getAuthorizedUser();
			if(Objects.isNull(userInformation))
				return false;
			return userInformation.getUsername().equals(userInformation.getUsername());
			
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in hasEntry method. ---------->>>>>", e.getMessage());
			return false;
		}
	}

	public boolean isUserApprovedActive() {
		try {
			UserInformation userInformation = getAuthorizedUser();
			if(Objects.isNull(userInformation))
				return false;
			return userInformation.isActive() && userInformation.getApprovalStatus().equals(EApprovalStatus.APPROVED);			
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in isUserApprovedActive method. ---------->>>>>", e.getMessage());
			return false;
		}
	}

	public boolean isContentOwner(String contentType, Long contentId) {
		try {
			UserInformation userInformation = getAuthorizedUser();
			if(Objects.isNull(userInformation))
				return false;
			if(contentType.equals("blog-post")) {
				Optional<BlogPost> blogPostOpt = blogPostRepository.findById(contentId);
				if(!blogPostOpt.isPresent())
					return false;
				return blogPostOpt.get().getPostCreator().getUsername().equals(userInformation.getUsername());
			}
			else if(contentType.equals("post-comment")) {
				Optional<Comment> commentOpt = commentRepository.findById(contentId);
				if(!commentOpt.isPresent())
					return false;
				return commentOpt.get().getCommentCreator().getUsername().equals(userInformation.getUsername());
			}
			else if(contentType.equals("reaction")) {
				Optional<PostReaction> postReactionOpt = postReactionRepository.findById(contentId);
				if(!postReactionOpt.isPresent())
					return false;
				return postReactionOpt.get().getReactorId().equals(userInformation.getUserId());
			}
			return false;
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in isContentOwner method. ---------->>>>>", e.getMessage());
			return false;
		}
	}
	
	public UserInformation getAuthorizedUser() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			return userInformationRepository.findByUsername(userDetails.getUsername());			
		} catch (Exception e) {
			logger.error("<<<<<---------- Exception {} has occured in getAuthorizedUser method. ---------->>>>>", e.getMessage());
			return null;
		}
	}
	
}

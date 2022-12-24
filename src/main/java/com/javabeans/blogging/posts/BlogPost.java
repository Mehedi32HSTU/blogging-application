package com.javabeans.blogging.posts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.javabeans.blogging.comments.Comment;
import com.javabeans.blogging.enums.EApprovalStatus;
import com.javabeans.blogging.users.UserInformation;

@Entity
@Table(name = "blog_post")
public class BlogPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "post_title")
	private String postTitle;

	@Column(name = "post_description")
	private String postDescription;

	@Enumerated(EnumType.STRING)
	@Column(name = "approval_status")
	private EApprovalStatus approvalStatus;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdDate;

	@ManyToOne
	@JoinColumn(name = "post_creator_id")
	@JsonIgnoreProperties({"roles"})
	private UserInformation postCreator;

	@OneToMany
	@JoinColumn(name = "blog_post_id")
	private List<Comment> comments = new ArrayList<>();

	@OneToMany
	@JoinColumn(name = "blog_post_id")
	private List<PostReaction> postReactions = new ArrayList<>();

	public BlogPost() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}

	public EApprovalStatus getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(EApprovalStatus approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public UserInformation getPostCreator() {
		return postCreator;
	}

	public void setPostCreator(UserInformation postCreator) {
		this.postCreator = postCreator;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<PostReaction> getPostReactions() {
		return postReactions;
	}

	public void setPostReactions(List<PostReaction> postReactions) {
		this.postReactions = postReactions;
	}

}

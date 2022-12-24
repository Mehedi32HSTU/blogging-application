package com.javabeans.blogging.posts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.javabeans.blogging.enums.EReaction;

@Entity
@Table(name = "post_reaction")
public class PostReaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "reaction")
	private EReaction reaction;

	@Column(name = "reactor_id")
	private Long reactorId;

	public PostReaction() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EReaction getReaction() {
		return reaction;
	}

	public void setReaction(EReaction reaction) {
		this.reaction = reaction;
	}

	public Long getReactorId() {
		return reactorId;
	}

	public void setReactorId(Long reactorId) {
		this.reactorId = reactorId;
	}

}

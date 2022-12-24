package com.javabeans.blogging.enums;

public enum EReaction {
	LIKE("LIKE"),
	DISLIKE("DISLIKE");

	private final String reaction;

	private EReaction(String reaction) {
		this.reaction = reaction;
	}

	public String getReaction() {
		return reaction;
	}
}

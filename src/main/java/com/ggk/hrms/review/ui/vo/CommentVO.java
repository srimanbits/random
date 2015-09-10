package com.ggk.hrms.review.ui.vo;


import java.io.Serializable;



public class CommentVO implements Serializable {
	private static final long serialVersionUID = 1L;


	private int commentId;

	private String commentText;
	

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

}
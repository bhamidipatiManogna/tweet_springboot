package com.tweetapp.model;

public class Comments {

	private String tweetId;
	private String loginId;
	private String comment;
	
	public Comments(String tweetId, String loginId, String comment) {
		super();
		this.setTweetId(tweetId);
		this.setLoginId(loginId);
		this.setComment(comment);
	}
	public Comments() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTweetId() {
		return tweetId;
	}
	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}

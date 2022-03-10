package com.tweetapp.model;

public class Likes {

	private String tweetId;
	private String loginId;
	
	public Likes(String tweetId, String loginId) {
		super();
		this.setTweetId(tweetId);
		this.setLoginId(loginId);
	}
	public Likes() {
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
}

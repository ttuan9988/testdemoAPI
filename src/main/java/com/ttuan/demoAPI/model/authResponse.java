package com.ttuan.demoAPI.model;

public class authResponse {
	public String username;
	public String accessToken;
	public String err;
	public authResponse() {
		
	}
	
	
	public authResponse(String username, String accessToken) {
		super();
		this.username = username;
		this.accessToken = accessToken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
	
}

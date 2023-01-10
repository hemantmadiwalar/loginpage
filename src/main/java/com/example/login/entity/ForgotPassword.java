package com.example.login.entity;



public class ForgotPassword {

	
	String userName;

	String password;
	
	
	
	public ForgotPassword(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String Password) {
		this.password = Password;
	}

	public ForgotPassword() {
		super();
	}

	

}

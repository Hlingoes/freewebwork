package com.cn.henry.freewebwork.core;

public class Message {

	public static final String SUCCESS = "alert-success";
	public static final String INFO = "alert-info";
	public static final String WARING = "alert-warning";
	public static final String ERROR = "alert-danger";

	private String state;
	private String message;

	public Message() {
	}

	public Message(String state, String message) {
		this.state = state;
		this.message = message;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

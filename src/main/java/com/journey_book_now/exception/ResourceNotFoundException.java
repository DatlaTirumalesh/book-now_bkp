package com.journey_book_now.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String msg;
	public ResourceNotFoundException(String message) {
		super(message);
		this.msg = message;
	}
}

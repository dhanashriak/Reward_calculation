package com.example.exception;

public class InvalidTransactionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8273600227052714658L;

	public InvalidTransactionException(String message) {
		super(message);
	}
}

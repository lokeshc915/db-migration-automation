package com.matilda.cloud.db.migration.automation.exception;

public class MediaTypeNotSupportedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MediaTypeNotSupportedException() {
		super();
	}

	public MediaTypeNotSupportedException(String message) {
		super(message);
	}

	public MediaTypeNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}
}

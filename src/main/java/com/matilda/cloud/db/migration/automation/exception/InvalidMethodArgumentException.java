package com.matilda.cloud.db.migration.automation.exception;

public class InvalidMethodArgumentException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidMethodArgumentException() {
        super();
    }

    public InvalidMethodArgumentException(String message) {
        super(message);
    }

    public InvalidMethodArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}

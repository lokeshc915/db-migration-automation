package com.matilda.cloud.db.migration.automation.exception;

public class NoContentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoContentException() {
        super();
    }

    public NoContentException(String message) {
        super(message);
    }

    public NoContentException(String message, Throwable cause) {
        super(message, cause);
    }

}

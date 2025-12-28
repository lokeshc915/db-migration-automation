package com.matilda.cloud.db.migration.automation.exception;


public class MatildaCloudException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MatildaCloudException() {
        super();
    }

    public MatildaCloudException(String message) {
        super(message);
    }

    public MatildaCloudException(String message, Throwable cause) {
        super(message, cause);
    }
}
package  com.matilda.cloud.db.migration.automation.exception;

public class DateParsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DateParsingException() {
        super();
    }

    public DateParsingException(String message) {
        super(message);
    }

    public DateParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}

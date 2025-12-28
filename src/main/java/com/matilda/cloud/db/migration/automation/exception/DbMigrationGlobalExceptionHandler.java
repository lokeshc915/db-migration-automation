package com.matilda.cloud.db.migration.automation.exception;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Migration application REST exception handlers defined at a global level
 * 
 * @author Lokesh Babu Cherukuri
 */
@ControllerAdvice
public class DbMigrationGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(DbMigrationGlobalExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
		ApiError apiError = new ApiError(status);
		apiError.setMessage("" + errorList);
		apiError.setErrorcode(status.value());
		LOG.error("Method argument invalid error ", ex);
		apiError.setDebugMessage(ex.getLocalizedMessage());
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	


	/**
	 * all unknown exceptions
	 */
	@ExceptionHandler({ Exception.class, ResponseStatusException.class })
	protected ResponseEntity<Object> handleGenericException(Exception ex) {
		return buildResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	

	private ResponseEntity<Object> buildResponseEntity(Exception exception, HttpStatus httpStatus) {
		LOG.error("Error caught: ", exception);
		ApiError apiError = new ApiError(httpStatus);
		apiError.setMessage(exception.getMessage());
		apiError.setErrorcode(httpStatus.value());
		apiError.setDebugMessage(exception.getLocalizedMessage());
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}

package com.aziphael.webapp.exception;

/**
 * @author aziphael
 * 
 */
public class LoadingException extends LogicalException {

	private static final long serialVersionUID = 1L;

	public LoadingException() {
		super();
	}

	public LoadingException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoadingException(String message) {
		super(message);
	}

	public LoadingException(Throwable cause) {
		super(cause);
	}

}

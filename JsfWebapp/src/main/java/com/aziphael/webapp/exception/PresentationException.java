package com.aziphael.webapp.exception;

public class PresentationException extends Exception {

	private static final long serialVersionUID = 1L;

	public PresentationException() {
		super();
	}

	public PresentationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PresentationException(String message) {
		super(message);
	}

	public PresentationException(Throwable cause) {
		super(cause);
	}
}

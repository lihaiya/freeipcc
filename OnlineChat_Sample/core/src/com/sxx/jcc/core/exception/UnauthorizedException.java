package com.sxx.jcc.core.exception;

public class UnauthorizedException extends AppBaseException {
	private static final long serialVersionUID = UnauthorizedException.class.hashCode();

	public UnauthorizedException() {
		super();
	}

	public UnauthorizedException(int code, String message) {
		super(code, message);
	}

	public UnauthorizedException(String message) {
		super(message);
	}

	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnauthorizedException(Throwable cause) {
		super(cause);
	}

	public UnauthorizedException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}
}

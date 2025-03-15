package com.sxx.jcc.core.exception;

public class AppBaseException extends BaseException {

	private static final long serialVersionUID = AppBaseException.class.hashCode();

	public AppBaseException(){
		super();
	}
	
	public AppBaseException(int code, String message) {
		super(code, message);
	}

	public AppBaseException(String message) {
		super(message);
	}

	public AppBaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppBaseException(Throwable cause) {
		super(cause);
	}
	
	public AppBaseException(int code, String message, Throwable cause) {
		super(code,message,cause);
	}
}

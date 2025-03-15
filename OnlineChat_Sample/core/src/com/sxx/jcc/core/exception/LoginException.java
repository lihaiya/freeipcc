package com.sxx.jcc.core.exception;

public class LoginException extends AppBaseException{

	private static final long serialVersionUID = LoginException.class.hashCode();
	public LoginException(){
		super();
	}
	
	public LoginException(int code, String message) {
		super(code, message);
	}
	
	public LoginException(String message) {
		super(message);
	}

	public LoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginException(Throwable cause) {
		super(cause);
	}
	
	public LoginException(int code, String message, Throwable cause) {
		super(code,message,cause);
	}
}

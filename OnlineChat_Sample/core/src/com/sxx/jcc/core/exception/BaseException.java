package com.sxx.jcc.core.exception;

public abstract class BaseException extends Exception {

	public static final int NO_ERROR = 0;

	private static final long serialVersionUID = BaseException.class.hashCode();
	int errorCode;
	String _err_string;

	public BaseException() {
		super();
	}

	/**
	 * @param message
	 */
	public BaseException(String message) {
		super(message);
		_err_string = message;
	}

	public BaseException(int code) {
		errorCode = code;
	}

	/**
	 * @param cause
	 */
	public BaseException(Throwable cause) {
		super(cause);
	}

	/**
	 * Construct with error code and message
	 * 
	 * @param code
	 * @param message
	 */
	public BaseException(int code, String message) {
		super(message);
		this.errorCode = code;
		_err_string = message;
	}

	/**
	 * Construct with error code, message and an exception that caused
	 * 
	 * @param code
	 * @param message
	 * @param cause
	 */
	public BaseException(int code, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = code;
		_err_string = message;
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public int getCode() {
		return this.errorCode;
	}

	public String getMesg() {
		return _err_string;
	}
}

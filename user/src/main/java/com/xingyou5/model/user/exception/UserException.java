package com.xingyou5.model.user.exception;

import com.xingyou5.model.base.exception.BaseException;


public class UserException extends BaseException {
	private final static long serialVersionUID = 6476473452739657628L;
    private String messageOrCode;
    
	public UserException() {
		super();
	}

	public UserException(String messageOrCode) {
		super(messageOrCode);
		this.messageOrCode = messageOrCode;
	}

	public UserException(String messageOrCode, String[] errorValues) {
		super(messageOrCode,errorValues);
		this.messageOrCode = messageOrCode;
	}

	public UserException(String messageOrCode, String errorValue) {
		super(messageOrCode,errorValue);
		this.messageOrCode = messageOrCode;
	}
	
	public UserException(String messageOrCode, Throwable cause) {
		super(messageOrCode, cause);
		this.messageOrCode = messageOrCode;
	}

	public UserException(Throwable cause) {
		super(cause);
	}

	public UserException(String messageOrCode, String[] errorValues,
			Throwable cause) {
		super(messageOrCode,errorValues, cause);
		this.messageOrCode = messageOrCode;
	}
	
	public UserException(String messageOrCode, String errorValue,
			Throwable cause) {
		super(messageOrCode,errorValue, cause);
		this.messageOrCode = messageOrCode;
	}
	
	public String getMessageOrCode(){
		return messageOrCode;
	}
}

package com.xingyou5.model.security.exception;

import com.xingyou5.model.base.exception.BaseException;


public class ZebraUserException extends BaseException {
	private final static long serialVersionUID = 6476473452739657628L;
    private String messageOrCode;
    
	public ZebraUserException() {
		super();
	}

	public ZebraUserException(String messageOrCode) {
		super(messageOrCode);
		this.messageOrCode = messageOrCode;
	}

	public ZebraUserException(String messageOrCode, String[] errorValues) {
		super(messageOrCode,errorValues);
		this.messageOrCode = messageOrCode;
	}

	public ZebraUserException(String messageOrCode, String errorValue) {
		super(messageOrCode,errorValue);
		this.messageOrCode = messageOrCode;
	}
	
	public ZebraUserException(String messageOrCode, Throwable cause) {
		super(messageOrCode, cause);
		this.messageOrCode = messageOrCode;
	}

	public ZebraUserException(Throwable cause) {
		super(cause);
	}

	public ZebraUserException(String messageOrCode, String[] errorValues,
			Throwable cause) {
		super(messageOrCode,errorValues, cause);
		this.messageOrCode = messageOrCode;
	}
	
	public ZebraUserException(String messageOrCode, String errorValue,
			Throwable cause) {
		super(messageOrCode,errorValue, cause);
		this.messageOrCode = messageOrCode;
	}
	
	public String getMessageOrCode(){
		return messageOrCode;
	}
}

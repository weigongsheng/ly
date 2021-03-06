/**
 * 
 */
package com.xingyou5.module.user.exception;

 
public class XingYou5UserException extends UserException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1634988678125536530L;

	/**
	 * 
	 */
	public XingYou5UserException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageOrCode
	 * @param errorValue
	 * @param cause
	 */
	public XingYou5UserException(String messageOrCode, String errorValue,
			Throwable cause) {
		super(messageOrCode, errorValue, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageOrCode
	 * @param errorValue
	 */
	public XingYou5UserException(String messageOrCode, String errorValue) {
		super(messageOrCode, errorValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageOrCode
	 * @param errorValues
	 * @param cause
	 */
	public XingYou5UserException(String messageOrCode, String[] errorValues,
			Throwable cause) {
		super(messageOrCode, errorValues, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageOrCode
	 * @param errorValues
	 */
	public XingYou5UserException(String messageOrCode, String[] errorValues) {
		super(messageOrCode, errorValues);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageOrCode
	 * @param cause
	 */
	public XingYou5UserException(String messageOrCode, Throwable cause) {
		super(messageOrCode, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageOrCode
	 */
	public XingYou5UserException(String messageOrCode) {
		super(messageOrCode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public XingYou5UserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}

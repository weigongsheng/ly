/**
 * 圆通新龙电子商务有限公司（www.ytoxl.com）
 */
package com.xingyou5.model.security.exception;

/**
 * @description 描述该类功能
 * @author 何碧波<a href="mailto:hebibo@ytoxl.com">hebibo@ytoxl.com</a>
 * @date 2013-12-4
 */
public class YtoxlUserException extends ZebraUserException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1634988678125536530L;

	/**
	 * 
	 */
	public YtoxlUserException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageOrCode
	 * @param errorValue
	 * @param cause
	 */
	public YtoxlUserException(String messageOrCode, String errorValue,
			Throwable cause) {
		super(messageOrCode, errorValue, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageOrCode
	 * @param errorValue
	 */
	public YtoxlUserException(String messageOrCode, String errorValue) {
		super(messageOrCode, errorValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageOrCode
	 * @param errorValues
	 * @param cause
	 */
	public YtoxlUserException(String messageOrCode, String[] errorValues,
			Throwable cause) {
		super(messageOrCode, errorValues, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageOrCode
	 * @param errorValues
	 */
	public YtoxlUserException(String messageOrCode, String[] errorValues) {
		super(messageOrCode, errorValues);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageOrCode
	 * @param cause
	 */
	public YtoxlUserException(String messageOrCode, Throwable cause) {
		super(messageOrCode, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageOrCode
	 */
	public YtoxlUserException(String messageOrCode) {
		super(messageOrCode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public YtoxlUserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}

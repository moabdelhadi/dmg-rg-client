package com.dmg.client.payment;

public class PaymentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -40601134217758525L;
	
	
	public PaymentException() {
		super();
	}


	public PaymentException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}


	public PaymentException(String message, Throwable cause) {
		super(message, cause);
	}


	public PaymentException(String message) {
		super(message);
	}


	public PaymentException(Throwable cause) {
		super(cause);
	}
	
}

package com.dmg.client.auth.util;

import java.io.Serializable;

public class ValidateMessage implements Serializable {

	/**
	 * @author Kareem Jabr
	 * 
	 */
	private static final long serialVersionUID = -8184185302500094595L;

	private boolean valid;
	private String message;

	public ValidateMessage(boolean valid, String message) {
		this.valid = valid;
		this.message = message;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

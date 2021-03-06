package com.dmg.client.auth.util;

/**
 * Thrown if a password validation fails due to the password not fulfilling the
 * set requirements. Requirements are set with system properties.
 * 
 * @author Kim
 * 
 */
public class PasswordRequirementException extends Exception {

	private static final long serialVersionUID = -1941971695928461855L;

	public PasswordRequirementException(String errMessage) {
		super(errMessage);
	}
}

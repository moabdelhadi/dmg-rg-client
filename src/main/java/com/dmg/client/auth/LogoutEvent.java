package com.dmg.client.auth;

import java.io.Serializable;

import com.dmg.core.bean.UserAccount;


/**
 * Interface for listening to logout events.
 * 
 * @author Kim
 * 
 */
public class LogoutEvent implements Serializable {

	private static final long serialVersionUID = -5497435495187618081L;

	private final UserAccount user;

	/**
	 * 
	 * @param user
	 *            The user who is been logged out
	 */
	public LogoutEvent(UserAccount user) {
		this.user = user;
	}

	/**
	 * Get the user who has been logged out
	 * 
	 * @return The user object of the logged out user
	 */
	public UserAccount getUser() {
		return user;
	}

}
package com.dmg.simplepayment.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dmg.simplepayment.beans.User;
import com.vaadin.server.VaadinService;

/**
 * A utility class for handling user sessions
 * 
 * @author Kim
 * 
 */
public class SessionHandler implements Serializable {

	private static final long serialVersionUID = 4142938996955537395L;

	private User user;

	// Store the user object of the currently inlogged user

	/**
	 * Constructor
	 * 
	 * @param ui
	 *            Current application instance
	 */
	public SessionHandler() {

		VaadinService.getCurrentRequest().getWrappedSession().setAttribute("-sessionhandler", this);
	}

	/**
	 * Set the User object for the currently inlogged user for this application
	 * instance
	 * 
	 * @param user
	 */
	public static void setUser(User user) {
		getCurrent().user = user;
	}

	/**
	 * Get the User object of the currently inlogged user for this application
	 * instance.
	 * 
	 * @return The currently inlogged user
	 */
	public static User get() {
		return getCurrent().user;
	}

	/**
	 * Method for logging out a user
	 */
	public static void logout() {
		setUser(null);
	}

	/**
	 * Initializes the {@link SessionHandler} for the given {@link Application}
	 * 
	 * @param ui
	 */
	public static void initialize() {
		new SessionHandler();
	}


	public static SessionHandler getCurrent() {

		SessionHandler handler = (SessionHandler) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("-sessionhandler");
		return handler;
	}

}

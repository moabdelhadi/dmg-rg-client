package com.dmg.client.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dmg.core.bean.NewUserRegistration;
import com.dmg.core.bean.UserAccount;
import com.vaadin.server.VaadinService;

/**
 * A utility class for handling user sessions
 * 
 * @author Kim
 * 
 */
public class SessionHandler implements Serializable {

	private static final long serialVersionUID = 4142938996955537395L;

	private UserAccount user;

	private final List<LogoutListener> listeners = new ArrayList<LogoutListener>();

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
	public static void setUser(UserAccount user) {
		SessionHandler current = getCurrent();
		if (current == null) {
			initialize();
		}
		getCurrent().user = user;
	}


	/**
	 * Get the User object of the currently inlogged user for this application
	 * instance.
	 * 
	 * @return The currently inlogged user
	 */
	public static UserAccount get() {

		SessionHandler current = getCurrent();
		if (current == null) {
			return null;
		}
		return current.user;
	}


	/**
	 * Method for logging out a user
	 */
	public static void logout() {
		LogoutEvent event = new LogoutEvent(getCurrent().user);
		setUser(null);
		dispatchLogoutEvent(event);
	}

	/**
	 * Dispatches the {@link LogoutEvent} to all registered logout listeners.
	 * 
	 * @param event
	 *            The LogoutEvent
	 */
	private static void dispatchLogoutEvent(LogoutEvent event) {
		for (LogoutListener listener : getCurrent().listeners) {
			listener.logout(event);
		}
	}

	/**
	 * Initializes the {@link SessionHandler} for the given {@link Application}
	 * 
	 * @param ui
	 */
	public static void initialize() {
		new SessionHandler();
	}

	/**
	 * Add a logout listener.
	 * 
	 * @param listener
	 */
	public static void addListener(LogoutListener listener) {
		getCurrent().listeners.add(listener);
	}

	/**
	 * Remove the given listener from the active logout listeners.
	 * 
	 * @param listener
	 */
	public static void removeListener(LogoutListener listener) {
		getCurrent().listeners.remove(listener);
	}

	public static SessionHandler getCurrent() {

		SessionHandler handler = (SessionHandler) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("-sessionhandler");

		return handler;
	}

}

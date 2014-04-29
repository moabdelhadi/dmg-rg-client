//package com.alarabiya.twitifier.auth.util;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.security.auth.login.AccountLockedException;
//
//import com.alarabiya.twitifier.auth.SessionHandler;
//import com.alarabiya.twitifier.bean.User;
//import com.alarabiya.twitifier.exception.InvalidCredentialsException;
//import com.alarabiya.twitifier.persistence.FacadeFactory;
//
//public class AuthenticationUtil {
//	/**
//	 * Try to log in a user with the given user credentials
//	 * 
//	 * @param username
//	 *            Username of the user
//	 * @param password
//	 *            Password of the user
//	 * @return The authenticated user object
//	 * @throws InvalidCredentialsException
//	 *             Thrown if the crendentials are incorrect
//	 */
//	public static User authenticate(String username, String password) throws InvalidCredentialsException, AccountLockedException {
//		// Login fails if either the username or password is null
//		if (username == null || password == null) {
//			throw new InvalidCredentialsException();
//		}
//
//		User user = getUserForUsername(username);
//
//		if (user == null) {
//			// Invalid username
//			throw new InvalidCredentialsException();
//		}
//
//		if (user.isAccountLocked()) {
//			throw new AccountLockedException();
//		}
//
//		if (!PasswordUtil.verifyPassword(user, password)) {
//			// Invalid password
//			incrementFailedLoginAttempts(user);
//			throw new InvalidCredentialsException();
//		}
//		// The user's password was correct, so set the user as the
//		// current user (inlogged)
//		SessionHandler.setUser(user);
//		clearFailedLoginAttempts(user);
//
//		return user;
//	}
//
//	/**
//	 * Fetches the User object from the database for the given username
//	 * 
//	 * @param username
//	 * @return User object for username
//	 */
//	private static User getUserForUsername(String username) {
//		// Create a query which searches the database for a user with the given
//		// username
//		String query = "SELECT u FROM User u WHERE u.username = :username";
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("username", username);
//		return (User) FacadeFactory.getFacade().find(query, parameters);
//	}
//
//	/**
//	 * Clear the number of failed login attempts if the user has any failed
//	 * attempts.
//	 * 
//	 * @param user
//	 *            The user whose failed login attempt record should be cleared
//	 */
//	private static void clearFailedLoginAttempts(User user) {
//		if (user.getFailedLoginAttempts() > 0) {
//			user.clearFailedLoginAttempts();
//			FacadeFactory.getFacade().store(user);
//		}
//	}
//
//	/**
//	 * Increment the user's number of failed login attempts by one
//	 * 
//	 * @param user
//	 */
//	private static void incrementFailedLoginAttempts(User user) throws AccountLockedException {
//		user.incrementFailedLoginAttempts();
//		try {
//			if (user.getFailedLoginAttempts() > numberOfAllowedFailedLoginAttempts()) {
//				user.setAccountLocked(true);
//				user.setReasonForLockedAccount("tooManyLoginAttempts");
//				throw new AccountLockedException();
//			}
//		} finally {
//			FacadeFactory.getFacade().store(user);
//		}
//	}
//
//	/**
//	 * Returns the maximum number of consecutive failed login attempts.
//	 * 
//	 * @return
//	 */
//	private static int numberOfAllowedFailedLoginAttempts() {
//		String maxAttemptsStr = PropertiesManager.getInstance().getProperty("authentication.maxFailedLoginAttempts");
//		int maxAttempts = 5;
//		if (maxAttemptsStr == null) {
//			//System.setProperty("authentication.maxFailedLoginAttempts", "5");
//			return maxAttempts;
//		}
//
//		try {
//			maxAttempts = Integer.valueOf(maxAttemptsStr);
//		} catch (NumberFormatException e) {
//			throw new IllegalArgumentException("authentication.maxFailedLoginAttempts must be an integer");
//		}
//
//		return maxAttempts;
//	}
//}

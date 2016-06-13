package com.dmg.client.auth.util;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.dmg.core.bean.UserAccount;
import com.dmg.util.PropertiesManager;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.TextField;

/**
 * Utility class containing useful helper methods related to passwords.
 * 
 * @author Kim
 * 
 */
public class PasswordUtil implements Serializable {

	private static final long serialVersionUID = 7823991334001022227L;

	// Store the password salt in a static variable
	private static String salt = null;

	/**
	 * Get the salt value for the passwords
	 * 
	 * @return
	 */
	protected static String getSalt() {
		// Check if the salt has been set. If not, then create a default salt
		// value.
		String systemSalt = PropertiesManager.getInstance().getProperty("authentication.password.salt");
		//String systemSalt = ")%gersK43q5)=%3qiyt34389py43pqhgwer8l9";
		if (salt != null && !salt.equals(systemSalt)) {
			throw new UnsupportedOperationException("Password salt is already set");
		}

		if (salt == null && systemSalt != null) {
			salt = systemSalt;
		}

		if (salt == null) {
			salt = ")%gersK43q5)=%3qiyt34389py43pqhgwer8l9";
			// System.setProperty("authentication.password.salt", salt);
		}

		return salt;
	}

	/**
	 * Verify if the given password (unhashed) matches with the user's password
	 * 
	 * @param user
	 *            User to whome's password we are comparing
	 * @param password
	 *            The unhashed password we are comparing
	 * @return Returns true if passwords match, otherwise false
	 */
	public static boolean verifyPassword(UserAccount user, String password) {
		// Return null if either the username or password is null
		if (user == null || password == null) {
			return false;
		}

		// Hash the generated password
		String hashedPassword = generateHashedPassword(password);

		// Check if the password matches with the one stored in the User object
		if (user.getPassword().equals(hashedPassword)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Generates a hashed password of the given string.
	 * 
	 * @param password
	 *            String which is to be hashed
	 * @return Hashed password
	 */
	public static String generateHashedPassword(String password) {
		StringBuffer hashedPassword = new StringBuffer();

		// Get a byte array of the password concatenated with the password salt
		// value
		byte[] defaultBytes = (password + getSalt()).getBytes();
		try {
			// Perform the hashing with SHA
			MessageDigest algorithm = MessageDigest.getInstance("SHA");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();

			for (int i = 0; i < messageDigest.length; i++) {
				hashedPassword.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
		} catch (NoSuchAlgorithmException nsae) {

		}

		return hashedPassword.toString();
	}

	/**
	 * Validates that the password has met all set requirements
	 * 
	 * @param password
	 *            String to be checked
	 * @return True if password meets all requirements, false if not
	 * @throws PasswordRequirementException
	 */
	public static void isValid(String password) throws PasswordRequirementException {
		List<Validator> validators = getValidators();
		for (Validator validator : validators) {
			try {
				validator.validate(password);
			} catch (InvalidValueException e) {
				throw new PasswordRequirementException(e.getMessage());

			}
		}

	}

	/**
	 * Returns a list of {@link Validator} objects which can be attached to
	 * Vaadin {@link TextField}s. The Validators are built based on the the
	 * paswword requirements defined.
	 * 
	 * @return List of validators
	 */
	public static List<Validator> getValidators() {
		List<Validator> validators = new ArrayList<Validator>();

		validators.add(new StringLengthValidator("Password is too short", getMinPasswordLength(), 999999, false));

		if (isLowerCaseRequired()) {
			validators.add(new RegexpValidator(".*[a-z].*", "The password must contain lower case letters (a-z)"));
		}

		if (isUpperCaseRequired()) {
			validators.add(new RegexpValidator(".*[A-Z].*", "The password must contain upper case latters (A-Z)"));
		}

		if (isNumericRequired()) {
			validators.add(new RegexpValidator(".*[0-9].*", "The password must contain numbers)"));
		}

		if (isSpecialCharacterRequired()) {
			validators.add(new RegexpValidator(".*[^a-zA-Z0-9].*", "The password must contain characters other than " + "letters from A to Z or numbers"));
		}
		return validators;
	}

	/**
	 * Checks if lower case letters (a-z) are required to be present in the
	 * password.
	 * 
	 * @return True if lower case letters are required, otherwise false
	 */
	private static boolean isLowerCaseRequired() {
		return Boolean.parseBoolean(PropertiesManager.getInstance().getProperty("authentication.password.validation.lowerCaseRequired"));
	}

	/**
	 * Checks if upper case letters (A-Z) are required to be present in the
	 * password.
	 * 
	 * @return True if upper case letters are required, otherwise false
	 */
	private static boolean isUpperCaseRequired() {
		return Boolean.parseBoolean(PropertiesManager.getInstance().getProperty("authentication.password.validation.upperCaseRequired"));
	}

	/**
	 * Checks if numbers (0-9) are required to be present in the password.
	 * 
	 * @return True if numbers are required, otherwise false
	 */
	private static boolean isNumericRequired() {
		return Boolean.parseBoolean(PropertiesManager.getInstance().getProperty("authentication.password.validation.numericRequired"));
	}

	/**
	 * Checks if special characters (anything else than numbers and letters from
	 * a to z) are required to be present in the password.
	 * 
	 * @return True if special characters are required, otherwise false
	 */
	private static boolean isSpecialCharacterRequired() {
		return Boolean.parseBoolean(PropertiesManager.getInstance().getProperty("authentication.password.validation.specialCharacterRequired"));
	}

	/**
	 * Returns the minimum length of a password
	 * 
	 * @return Minimum password length
	 */
	public static int getMinPasswordLength() {
		String minLenghtStr = PropertiesManager.getInstance().getProperty("authentication.password.validation.length");
		int minLenght = 8;
		if (minLenghtStr == null) {
			// System.setProperty("authentication.password.validation.length",
			// "8");
			return minLenght;
		}

		try {
			minLenght = Integer.valueOf(minLenghtStr);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("authentication.password.validation.length must be an integer");
		}

		return minLenght;
	}
}

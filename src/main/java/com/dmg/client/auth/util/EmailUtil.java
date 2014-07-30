package com.dmg.client.auth.util;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;

public class EmailUtil {

	public static void validateEmail(String email) throws EmailInvalidException {
		List<Validator> validators = new ArrayList<Validator>();

		validators.add(new EmailValidator("Email is invalid"));
		try {
			for (Validator validator : validators) {
				validator.validate(email);
			}

		} catch (InvalidValueException e) {
			throw new EmailInvalidException();
		}

	}

}

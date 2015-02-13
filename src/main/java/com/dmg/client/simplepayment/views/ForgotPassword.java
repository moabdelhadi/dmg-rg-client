package com.dmg.client.simplepayment.views;

import java.util.HashMap;
import java.util.Map;

import com.dmg.client.simplepayment.beans.Constants;
import com.dmg.client.simplepayment.beans.UserAccount;
import com.dmg.client.user.UserManager;
import com.dmg.core.exception.DataAccessLayerException;
import com.dmg.util.SHAEncrypt;
import com.dmg.util.mail.MailManager;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.server.AbstractErrorMessage.ContentMode;
import com.vaadin.server.ErrorMessage.ErrorLevel;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Runo;

/**
 * This Should be a pop up to enter the email
 * 
 * @author mabdelhadi
 * 
 */
public class ForgotPassword extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TextField accountNumberField;
	private Button resetPasswordButton;
	private ComboBox citySelect;
	private Notification notification;
	private CustomLayout layout;

	/** Login Fileds **/

	private final Navigator navigator;
	private UserAccount userAccount;

	public ForgotPassword(Navigator navigator) {
		// this.fragmentAndParameters = null;
		this.navigator = navigator;
		init();
	}

	public ForgotPassword(Navigator navigator, String fragmentAndParameters) {
		// this.fragmentAndParameters = fragmentAndParameters;
		this.navigator = navigator;
		init();
	}

	private void init() {

		notification = new Notification("Email Sent Successfully", "Please check your email to reset your password", Notification.Type.HUMANIZED_MESSAGE);
		notification.setDelayMsec(5000);

		layout = new CustomLayout("forgotPassword");

		citySelect = new ComboBox("Region");
		citySelect.setHeight("30px");
		// loginCitySelect.setWidth("200px");
		citySelect.setStyleName("h2");
		citySelect.setCaption("Region");
		citySelect.setRequired(true);
		citySelect.setRequiredError("Please select your region");
		citySelect.setNullSelectionAllowed(false);
		citySelect.addItem("DUBAI");
		citySelect.addItem("ABUDHABI");
		citySelect.setItemCaption("DUBAI", "Dubai & Northern Emirates");
		citySelect.setItemCaption("ABUDHABI", "Abu dhabi, Alain & Western Region");
		citySelect.setInputPrompt("Please select your region");
		citySelect.setTextInputAllowed(false);
		layout.addComponent(citySelect, "userCity");

		accountNumberField = new TextField("Account No.");
		accountNumberField.setHeight("30px");
		accountNumberField.setStyleName("h2");
		accountNumberField.setInputPrompt("Please enter you account no. as shown in the help button");
		accountNumberField.setRequired(true);
		accountNumberField.setRequiredError("Please enter account number");
		// loginAccountId.setWidth("200px");
		layout.addComponent(accountNumberField, "accountNumber");

		addSendEmailButton();

		addComponent(layout);

	}

	private void addSendEmailButton() {
		resetPasswordButton = new Button("Reset Password");
		resetPasswordButton.addStyleName(Runo.BUTTON_BIG);
		resetPasswordButton.setDisableOnClick(true);
		resetPasswordButton.setClickShortcut(KeyCode.ENTER);
		resetPasswordButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -1410371220573383927L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (validate()) {
					String hashKey = SHAEncrypt.encryptKey(userAccount.getCity() + "_" + userAccount.getContractNo() + "_" + System.currentTimeMillis());
					userAccount.setPassResetKey(hashKey);
					try {
						UserManager.getInstance().updateAccount(userAccount);
					} catch (DataAccessLayerException e) {
						Notification.show("Error", e.getMessage(), Type.ERROR_MESSAGE);
						navigator.navigateTo(Views.LOGIN);
						return;
					}
					MailManager.getInstance().sendFromGmail(
							userAccount.getEmail(),
							"Reset Password",
							"Please click here: http://www.localhost:8080/dmg-rg-client/client/#!forgotPassword/" + hashKey + "/" + userAccount.getCity() + "/" + userAccount.getContractNo()
									+ " to reset your password");
					notification.show(Page.getCurrent());
					navigator.navigateTo(Views.LOGIN);
				}
				resetPasswordButton.setEnabled(true);

			}
		});
		layout.addComponent(resetPasswordButton, "resetPassword");
	}

	private boolean validate() {

		boolean status = true;
		resetFormValidation();

		try {
			accountNumberField.validate();
		} catch (InvalidValueException e) {
			accountNumberField.setComponentError(new UserError(null, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}
		try {
			citySelect.validate();
		} catch (InvalidValueException e) {
			citySelect.setComponentError(new UserError(null, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		if (status) {

			try {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put(Constants.USER_ACCOUNT_ID, accountNumberField.getValue());
				parameters.put(Constants.USER_CITY, citySelect.getValue().toString());
				userAccount = UserManager.getInstance().validateAccount(parameters);
				if (userAccount.getEmail() == null) {
					Notification.show("Error", "There is no email assigned to this user please call: 800-RGAS ", Type.ERROR_MESSAGE);
				}
			} catch (DataAccessLayerException e) {
				Notification.show("Error", e.getMessage(), Type.ERROR_MESSAGE);
				status = false;
			}

		}
		return status;
	}

	private void resetFormValidation() {
		accountNumberField.setComponentError(null);
		citySelect.setComponentError(null);
	}

	@Override
	public void enter(ViewChangeEvent event) {

		String parameters = event.getParameters();
		if ("".equals(parameters)) {
			return;
		}
		String[] params = parameters.split("/");
		if (params.length == 3) {
			String key = params[0];
			String city = params[1];
			String accountNo = params[2];
			try {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put(Constants.USER_ACCOUNT_ID, accountNo);
				paramMap.put(Constants.USER_CITY, city);
				paramMap.put(Constants.PASS_RESET_KEY, key);
				userAccount = UserManager.getInstance().validateAccount(paramMap);
				citySelect.setValue(userAccount.getCity());
				accountNumberField.setValue(userAccount.getContractNo());
				citySelect.setEnabled(false);
				accountNumberField.setEnabled(false);
				appendChangePasswordFields();
			} catch (DataAccessLayerException e) {
				Notification.show("Error", "Link is invalid", Type.ERROR_MESSAGE);
				navigator.navigateTo(Views.LOGIN);
			}

		} else {
			Notification.show("Error", "URL is malformed", Type.ERROR_MESSAGE);
			navigator.navigateTo(Views.LOGIN);
		}

	}

	private void appendChangePasswordFields() {
		final PasswordField passwordField = new PasswordField("Password");

		passwordField.setHeight("30px");
		// loginPassword.setWidth("200px");
		passwordField.setStyleName("h2");
		passwordField.setRequired(true);
		passwordField.setRequiredError("please enter your password");
		passwordField.setInputPrompt("Password");
		layout.addComponent(passwordField, "userPassword");

		final PasswordField confirmPassword = new PasswordField("Confirm Password");

		confirmPassword.setHeight("30px");
		// loginPassword.setWidth("200px");
		confirmPassword.setStyleName("h2");
		confirmPassword.setRequired(true);
		confirmPassword.setRequiredError("Please confirm your password");
		confirmPassword.setInputPrompt("Password");
		layout.addComponent(confirmPassword, "confirmPassword");

		resetPasswordButton = new Button("Reset Password");
		resetPasswordButton.addStyleName(Runo.BUTTON_BIG);
		resetPasswordButton.setDisableOnClick(true);
		resetPasswordButton.setClickShortcut(KeyCode.ENTER);
		layout.addComponent(resetPasswordButton, "resetPassword");

		resetPasswordButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7701526804523400506L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (validatePasswords(passwordField, confirmPassword)) {
					userAccount.setPassResetKey("");
					userAccount.setPassword(passwordField.getValue());
					try {
						UserManager.getInstance().updateAccount(userAccount);
						Notification.show("Success", "Your password has been successfully changed", Type.HUMANIZED_MESSAGE);
						addSendEmailButton();
						layout.removeComponent(passwordField);
						layout.removeComponent(confirmPassword);
						citySelect.setEnabled(true);
						accountNumberField.setEnabled(true);
						navigator.navigateTo(Views.LOGIN);
					} catch (DataAccessLayerException e) {
						Notification.show("Error", e.getMessage(), Type.ERROR_MESSAGE);
						navigator.navigateTo(Views.LOGIN);
					}
				}
				resetPasswordButton.setEnabled(true);
			}

		});

	}

	private boolean validatePasswords(PasswordField passwordField, PasswordField confirmPassword) {
		boolean valid = true;
		passwordField.setComponentError(null);
		confirmPassword.setComponentError(null);
		try {
			passwordField.validate();
		} catch (InvalidValueException e) {
			passwordField.setComponentError(new UserError(null, ContentMode.HTML, ErrorLevel.ERROR));
			valid = false;
		}
		try {
			confirmPassword.validate();
		} catch (InvalidValueException e) {
			confirmPassword.setComponentError(new UserError(null, ContentMode.HTML, ErrorLevel.ERROR));
			valid = false;
		}
		if (valid && !(passwordField.getValue().equals(confirmPassword.getValue()))) {
			confirmPassword.setComponentError(new UserError("Password must match"));
			Notification.show("Error", "Password don't match", Type.ERROR_MESSAGE);
			valid = false;
		}
		return valid;
	}

}

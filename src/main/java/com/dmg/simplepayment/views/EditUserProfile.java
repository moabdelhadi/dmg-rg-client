package com.dmg.simplepayment.views;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import com.dmg.core.exception.DataAccessLayerException;
import com.dmg.core.persistence.FacadeFactory;
import com.dmg.simplepayment.beans.UserAccount;
import com.dmg.simplepayment.beans.UserStatus;
import com.dmg.util.Logger;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.server.AbstractErrorMessage.ContentMode;
import com.vaadin.server.ErrorMessage.ErrorLevel;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class EditUserProfile extends VerticalLayout implements View {

	private Navigator navigator;
	private Button registerButton;
	private TextField loginEmail;
	private TextField name;
	private TextField poBox;
	private TextField poBoxCity;
	private PasswordField newPassword;
	private PasswordField confirmPassword;
	private Label city;
	private Label appartmentNumber;
	private Label buildingNumber;
	private Label contractNo;
	private TextField phone;
	private TextField mobile;
	// private TextField status;
	private UserAccount user;

	public EditUserProfile(Navigator navigator) {
		this.navigator = navigator;
		init();
	}

	private void init() {

		setSizeFull();
		CustomLayout customLayout = new CustomLayout("register");
		customLayout.setHeight("760px");
		// customLayout.setWidth("20%");
		loginEmail = new TextField("User Email");
		loginEmail.setInputPrompt("User Email");
		loginEmail.setRequired(true);
		loginEmail.setRequiredError("This field is required");
		loginEmail.addValidator(new EmailValidator("Please enter Valid Email"));
		customLayout.addComponent(loginEmail, "userEmail");

		name = new TextField("Full Name");
		name.setInputPrompt("Full Name");
		name.setRequired(true);
		name.setRequiredError("This field is required");
		customLayout.addComponent(name, "fullName");

		poBox = new TextField("POBOX");
		poBox.setInputPrompt("POBOX");
		customLayout.addComponent(poBox, "poBox");

		poBoxCity = new TextField("POBOX CITY");
		poBoxCity.setInputPrompt("POBOX CITY");
		customLayout.addComponent(poBoxCity, "poBoxCity");

		newPassword = new PasswordField("New Password");
		newPassword.setInputPrompt("New Password");
		newPassword.setRequired(true);
		newPassword.setRequiredError("This field is required");
		customLayout.addComponent(newPassword, "newPassword");

		confirmPassword = new PasswordField("Confirm Password");
		confirmPassword.setInputPrompt("Confirm Password");
		confirmPassword.setRequired(true);
		confirmPassword.setRequiredError("This field is required");

		customLayout.addComponent(confirmPassword, "confirmPassword");

		city = new Label();
		customLayout.addComponent(city, "city");

		appartmentNumber = new Label();
		customLayout.addComponent(appartmentNumber, "appartmentNumber");

		buildingNumber = new Label("Building Number: 55");
		customLayout.addComponent(buildingNumber, "buildingNumber");

		contractNo = new Label("Account No.: 123");
		customLayout.addComponent(contractNo, "accountId");

		phone = new TextField("Phone No.");
		phone.setInputPrompt("Phone No.");
		customLayout.addComponent(phone, "phone");

		mobile = new TextField("Mobile No.");
		mobile.setInputPrompt("Mobile No.");
		mobile.setRequired(true);
		mobile.setRequiredError("This field is required");
		customLayout.addComponent(mobile, "mobile");

		registerButton = new Button("Register");
		// loginButton.addStyleName(Runo.BUTTON_BIG);
		// loginButton.setClickShortcut(KeyCode.ENTER);
		customLayout.addComponent(registerButton, "registerButton");

		addComponent(customLayout);

		registerButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				registerNewUser();
			}
		});

	}

	private void registerNewUser() {

		if (!vaildateRegister()) {
			return;
		}

		if (user == null) {
			Logger.error(this, "There is no User Values");
			return;
		}

		user.setName(name.getValue());
		user.setStatus(UserStatus.ACTIVE.value());
		user.setPobox(poBox.getValue());
		user.setPoboxCity(poBoxCity.getValue());

		// TODO setactivationString
		user.setActivationString("");
		user.setEmail(loginEmail.getValue());
		user.setLastUpdate(Calendar.getInstance().getTime());
		user.setPassword(newPassword.getValue());
		user.setPhone(phone.getValue());
		user.setMobile(mobile.getValue());
		user.setUpdateDate(Calendar.getInstance().getTime());

		try {
			FacadeFactory.getFacade().store(user);
			navigator.navigateTo(Views.ACTIVATION_PAGE);
		} catch (DataAccessLayerException e) {
			navigator.navigateTo(Views.EDIT_PROFILE_PAGE+"/"+user.getContractNo()+"/"+user.getCity());
		}

	}

	private boolean vaildateRegister() {

		boolean status = true;
		resetFormValidation();

		try {
			loginEmail.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			loginEmail.setComponentError(new UserError(htmlMessage, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		try {
			name.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			name.setComponentError(new UserError(htmlMessage, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		try {
			poBox.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			poBox.setComponentError(new UserError(htmlMessage, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		try {
			poBoxCity.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			poBoxCity.setComponentError(new UserError(htmlMessage, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		try {
			newPassword.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			newPassword.setComponentError(new UserError(htmlMessage, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		try {
			confirmPassword.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			confirmPassword.setComponentError(new UserError(htmlMessage, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		if (newPassword.getValue() != null && !newPassword.getValue().trim().isEmpty() && confirmPassword != null) {
			if (!newPassword.getValue().equals(confirmPassword.getValue())) {
				confirmPassword.setComponentError(new UserError("Password must match"));
				status = false;
			}
		}

		try {
			phone.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			phone.setComponentError(new UserError(htmlMessage, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		try {
			mobile.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			mobile.setComponentError(new UserError(htmlMessage, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		return status;

	}

	private void resetFormValidation() {

		loginEmail.setComponentError(null);
		newPassword.setComponentError(null);
		confirmPassword.setComponentError(null);
		contractNo.setComponentError(null);
		appartmentNumber.setComponentError(null);
		buildingNumber.setComponentError(null);
		city.setComponentError(null);
		phone.setComponentError(null);
		// status.setComponentError(null);

	}

	@Override
	public void enter(ViewChangeEvent event) {

		Logger.debug(this, "Get in Edit User Profile Entree");

		String parametersString = event.getParameters();
		Logger.debug(this, "Parameters=" + parametersString);

		String[] parameters = parametersString.split("/");

		if (parameters == null || parameters.length != 2) {
			Logger.error(this, "No Paratemeres Passed to this user or Parameters are error ");
			return;
		}

		if (StringUtils.isEmpty(parameters[0]) || StringUtils.isEmpty(parameters[1])) {
			Logger.error(this, "Paratemeres Value is in correct " + parameters[0] + " , " + parameters[1]);
			return;
		}

		UserAccount userAccount = new UserAccount();
		userAccount.setContractNo(parameters[0]);
		userAccount.setCity(parameters[1]);
		UserAccount accountFromAccountID = UserManager.getInstance().getAccountFromAccountID(userAccount);

		if (accountFromAccountID == null) {
			Logger.error(this, "No Valid user with this parameter");
			return;
		}

		user = accountFromAccountID;

		setUserValues();

	}

	private void setUserValues() {

		if (user.getCity() != null) {
			city.setValue(user.getCity());
		}

		if (user.getAppartmentNumber() != null) {
			appartmentNumber.setValue(user.getAppartmentNumber());
		}
		if (user.getBuildingNumber() != null) {
			buildingNumber.setValue(user.getBuildingNumber());
		}

		if (user.getContractNo() != null) {
			contractNo.setValue(user.getContractNo());
		}

		if (user.getName() != null) {
			name.setValue(user.getName());
		}

		if (user.getEmail() != null) {
			loginEmail.setValue(user.getEmail());
		}

		if (user.getPobox() != null) {
			poBox.setValue(user.getPobox());
		}

		if (user.getPoboxCity() != null) {
			poBoxCity.setValue(user.getPoboxCity());
		}

		if (user.getPhone() != null) {
			phone.setValue(user.getPhone());
		}

		if (user.getMobile() != null) {
			mobile.setValue(user.getMobile());
		}

	}

}

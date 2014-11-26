package com.dmg.client.simplepayment.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.simplepayment.beans.UserAccount;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.AbstractErrorMessage.ContentMode;
import com.vaadin.server.ErrorMessage.ErrorLevel;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

public class Login extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(Login.class);

	private Navigator navigator;

	/** Login Fileds **/
	private TextField loginAccountId;
	private ComboBox loginCitySelect;
	private PasswordField loginPassword;
	private Button loginButton;

	private TextField accountId;
	private ComboBox citySelect;
	private Button registerButton;
	private TextField buildingNo;
	private TextField apartmentNo;

	// private String[] loginResultMessages = { "Login Success",
	// "Invalid User Email Or Password",
	// "User not activated yet please register first" };
	// private String fragmentAndParameters;

	public Login(Navigator navigator) {
		// this.fragmentAndParameters = null;
		this.navigator = navigator;
		init();
	}

	public Login(Navigator navigator, String fragmentAndParameters) {
		// this.fragmentAndParameters = fragmentAndParameters;
		this.navigator = navigator;
		init();
	}

	private void init() {

		setSizeFull();
		CustomLayout customLayout = new CustomLayout("login");
		// customLayout.setWidth("20%");
		loginAccountId = new TextField("Account No.");
		loginAccountId.setHeight("30px");
		loginAccountId.setStyleName("h2");
		loginAccountId
				.setInputPrompt("Please enter you account no. as shown in the help button");
		loginAccountId.setRequired(true);
		loginAccountId.setRequiredError("Please enter account number");
		// loginAccountId.setWidth("200px");
		customLayout.addComponent(loginAccountId, "userAccount");

		loginCitySelect = new ComboBox("Region");
		loginCitySelect.setHeight("30px");
		// loginCitySelect.setWidth("200px");
		loginCitySelect.setStyleName("h2");
		loginCitySelect.setCaption("Region");
		loginCitySelect.setRequired(true);
		loginCitySelect.setRequiredError("Please select your region");
		loginCitySelect.setNullSelectionAllowed(false);
		loginCitySelect.addItem("DUBAI");
		loginCitySelect.addItem("ABUDHABI");
		loginCitySelect.setItemCaption("DUBAI", "Dubai & Northern Emirates");
		loginCitySelect.setItemCaption("ABUDHABI",
				"Abu dhabi, Alain & Western Region");
		loginCitySelect.setInputPrompt("Please select your region");
		loginCitySelect.setTextInputAllowed(false);
		customLayout.addComponent(loginCitySelect, "userCity");

		loginPassword = new PasswordField("Password");
		loginPassword.setHeight("30px");
		// loginPassword.setWidth("200px");
		loginPassword.setStyleName("h2");
		loginPassword.setRequired(true);
		loginPassword.setRequiredError("please enter you passwords");
		loginPassword.setInputPrompt("Password");
		customLayout.addComponent(loginPassword, "userPassword");

		loginButton = new Button("Login");
		loginButton.addStyleName(Runo.BUTTON_BIG);
		loginButton.setClickShortcut(KeyCode.ENTER);
		customLayout.addComponent(loginButton, "loginButton");

		// customLayout.setWidth("20%");
		accountId = new TextField("Account No.");
		accountId.setHeight("30px");
		accountId.setStyleName("h2");
		accountId.setRequired(true);
		accountId
				.setInputPrompt("Please enter you account no. as shown in the help button");
		accountId.setRequiredError("This feild is required");
		customLayout.addComponent(accountId, "accountId");

		citySelect = new ComboBox("Region");
		citySelect.setHeight("30px");
		// citySelect.setWidth("200px");
		citySelect.setStyleName("h2");
		citySelect.setCaption("Region");
		citySelect.setRequired(true);
		citySelect.setRequiredError("Please select your region");
		citySelect.addItem("DUBAI");
		citySelect.addItem("ABUDHABI");
		citySelect.setItemCaption("DUBAI", "Dubai & Northern Emirates");
		citySelect.setItemCaption("ABUDHABI",
				"Abu dhabi, Alain & Western Region");
		citySelect.setInputPrompt("Please select your region");
		citySelect.setNullSelectionAllowed(false);
		citySelect.setTextInputAllowed(false);
		customLayout.addComponent(citySelect, "city");

		buildingNo = new TextField("Building No.");
		buildingNo.setHeight("30px");
		buildingNo.setStyleName("h2");
		buildingNo.setInputPrompt("Please enter building no. as shown in the help button");
		buildingNo.setRequired(true);
		buildingNo.setRequiredError("This feild is required");
		customLayout.addComponent(buildingNo, "buildingNo");

		apartmentNo = new TextField("Apartment No.");
		apartmentNo.setHeight("30px");
		apartmentNo.setStyleName("h2");
		apartmentNo.setInputPrompt("Please enter apartment no. as shown in the help button");
		apartmentNo.setRequired(true);
		apartmentNo.setRequiredError("This feild is required");
		customLayout.addComponent(apartmentNo, "apartmentNo");

		registerButton = new Button("Register");
		// loginButton.addStyleName(Runo.BUTTON_BIG);
		// loginButton.setClickShortcut(KeyCode.ENTER);
		customLayout.addComponent(registerButton, "registerButton");

		addComponent(customLayout);

		loginButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 3248886901323015804L;

			@Override
			public void buttonClick(ClickEvent event) {
				loginUser();
				// System.out.println("dfsfdsdfsdf");
				// FormSenderBuilder.create().withUI(getUI()).withAction("http://alarabiya.net").withMethod(Method.POST).withTarget("_blank").withValue("name",
				// "asdasdasd")
				// .withValue("password", "asdasdasd").submit();
				// System.out.println("zzzzzz");
			}
		});

		registerButton.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
		UserAccount user = new UserAccount();
		user.setContractNo(accountId.getValue());
		user.setCity(citySelect.getValue().toString());
		logger.info("try get  User With accountId = " + accountId.getValue()
				+ " and city =" + citySelect.getValue() + " ,  apartment="
				+ apartmentNo.getValue() + " , Building="
				+ buildingNo.getValue());

		UserAccount userAccount = UserManager.getInstance()
				.getAccountFromAccountID(user);
		if (userAccount == null) {
			logger.warn("No User With accountId = " + accountId.getValue());
			Notification.show("ERROR",
					"The input Data does not match an exsisting account",
					Notification.Type.HUMANIZED_MESSAGE);
			return;
		}

		if (userAccount.getStatus() != 0) {
			Notification.show("ERROR",
					"This User Already registered, Please Login",
					Notification.Type.HUMANIZED_MESSAGE);
			return;
		}

		if (!apartmentNo.getValue().equals(userAccount.getAppartmentNumber())) {
			logger.warn("Apartment Does not match");
			Notification.show("ERROR",
					"The input Data does not match an exsisting account",
					Notification.Type.HUMANIZED_MESSAGE);
			return;
		}

		if (!buildingNo.getValue().equals(userAccount.getBuildingNumber())) {
			Notification.show("ERROR", "Building Does not match",
					Notification.Type.HUMANIZED_MESSAGE);
			return;
		}

		navigator.navigateTo(Views.EDIT_PROFILE_PAGE + "/"
				+ userAccount.getContractNo() + "/" + userAccount.getCity());
	}

	private boolean vaildateRegister() {

		boolean status = true;
		resetFormValidation();

		try {
			accountId.validate();
		} catch (InvalidValueException e) {

			String htmlMessage = e.getHtmlMessage();
			accountId.setComponentError(new UserError(htmlMessage,
					ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		try {
			citySelect.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			citySelect.setComponentError(new UserError(htmlMessage,
					ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		try {
			buildingNo.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			buildingNo.setComponentError(new UserError(htmlMessage,
					ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		try {
			apartmentNo.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			apartmentNo.setComponentError(new UserError(htmlMessage,
					ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		return status;

	}

	private boolean validateLogin() {

		boolean status = true;
		resetFormValidation();

		try {
			loginCitySelect.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			loginCitySelect.setComponentError(new UserError(htmlMessage,
					ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		try {
			loginAccountId.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			loginAccountId.setComponentError(new UserError(htmlMessage,
					ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		try {
			loginPassword.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			loginPassword.setComponentError(new UserError(htmlMessage,
					ContentMode.HTML, ErrorLevel.ERROR));
			// loginPassword.setComponentError(new
			// UserError("This Field is required"));
			status = false;
		}
		return status;
	}

	private void resetFormValidation() {

		loginAccountId.setComponentError(null);
		loginPassword.setComponentError(null);
		loginCitySelect.setComponentError(null);

		accountId.setComponentError(null);
		citySelect.setComponentError(null);
		apartmentNo.setComponentError(null);
		buildingNo.setComponentError(null);

	}

	private void resetFormValues() {

		loginAccountId.setValue("");
		loginPassword.setValue("");
		loginCitySelect.setValue(null);

		accountId.setValue("");
		citySelect.setValue(null);
		apartmentNo.setValue("");
		buildingNo.setValue("");

	}

	private void loginUser() {

		if (!validateLogin()) {
			return;
		}

		UserAccount user = new UserAccount(loginAccountId.getValue(),
				loginPassword.getValue(), loginCitySelect.getValue().toString());

		try {
			UserAccount result = UserManager.getInstance().login(user);
			if (result != null) {
				navigator.navigateTo(Views.USER_PAGE + "/"
						+ result.getContractNo() + "/" + result.getCity());
			}

		} catch (Exception e) {
			logger.error("Error in login", e);
			Notification.show("Error", e.getMessage(), Type.HUMANIZED_MESSAGE);
		}

		// {
		// Notification.show("Error", loginResultMessages[result],
		// Type.HUMANIZED_MESSAGE);
		// }

	}

	@Override
	public void enter(ViewChangeEvent event) {

		resetFormValidation();
		resetFormValues();

		logger.debug("Get IN LOGIN VIEW");
		System.out.println("get in login");

	}

}

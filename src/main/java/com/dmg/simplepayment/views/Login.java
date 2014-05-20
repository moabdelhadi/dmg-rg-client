package com.dmg.simplepayment.views;

import com.dmg.simplepayment.beans.UserAccount;
import com.dmg.simplepayment.beans.UserStatus;
import com.dmg.util.EncryptionUtil;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.Runo;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class Login extends VerticalLayout implements View {

	private Navigator navigator;

	/** Login Fileds **/
	private TextField loginEmail;
	private PasswordField loginPassword;
	private Button loginButton;

	private TextField accountId;
	private ComboBox citySelect;
	private Button registerButton;

	public Login(Navigator navigator) {
		this.navigator = navigator;
		init();
	}

	private void init() {

		setSizeFull();
		CustomLayout customLayout = new CustomLayout("login");
		// customLayout.setWidth("20%");
		loginEmail = new TextField("User Email");
		loginEmail.setInputPrompt("User Email");
		customLayout.addComponent(loginEmail, "userEmail");

		loginPassword = new PasswordField("Password");
		loginPassword.setInputPrompt("Password");
		customLayout.addComponent(loginPassword, "userPassword");

		loginButton = new Button("Login");
		loginButton.addStyleName(Runo.BUTTON_BIG);
		loginButton.setClickShortcut(KeyCode.ENTER);
		customLayout.addComponent(loginButton, "loginButton");

		// customLayout.setWidth("20%");
		accountId = new TextField("Account No.");
		accountId.setInputPrompt("Account No.");
		customLayout.addComponent(accountId, "accountId");

		citySelect = new ComboBox("City");
		citySelect.addItem("DUBAI");
		citySelect.addItem("ABUDHABI");
		citySelect.setInputPrompt("City");
		customLayout.addComponent(citySelect, "city");

		registerButton = new Button("Register");
		// loginButton.addStyleName(Runo.BUTTON_BIG);
		// loginButton.setClickShortcut(KeyCode.ENTER);
		customLayout.addComponent(registerButton, "registerButton");

		addComponent(customLayout);

		loginButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				loginUser();
			}
		});

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
		UserAccount user = new UserAccount();
		user.setAccountId(accountId.getValue());
		user.setCity(citySelect.getValue().toString());
		
		UserAccount userAccount = UserManager.getInstance().getAccountFromAccountID(user);
		if(userAccount!=null){
			navigator.navigateTo(Views.EDIT_PROFILE_PAGE);
		}else{
			Notification.show("ERROR", "Erro in account id or City",Notification.Type.ERROR_MESSAGE);
		}
	}


	private boolean vaildateRegister() {

		boolean status = true;
		resetFormValidation();

		try {
			accountId.validate();
		} catch (InvalidValueException e) {
			accountId.setComponentError(new UserError("This Field is required"));
			status = false;
		}

		try {
			citySelect.validate();
		} catch (InvalidValueException e) {
			status = false;
		}
		return status;

	}

	private boolean validateLogin() {

		boolean status = true;
		resetFormValidation();

		try {
			loginEmail.validate();
		} catch (InvalidValueException e) {
			loginEmail.setComponentError(new UserError("This Field is required"));
			status = false;
		}

		try {
			loginPassword.validate();
		} catch (InvalidValueException e) {
			loginPassword.setComponentError(new UserError("This Field is required"));
			status = false;
		}
		return status;
	}

	private void resetFormValidation() {

		loginEmail.setComponentError(null);
		loginPassword.setComponentError(null);

		accountId.setComponentError(null);
		citySelect.setComponentError(null);

	}

	private void loginUser() {

		if (!validateLogin()) {
			return;
		}
		
		UserAccount user = new UserAccount(loginEmail.getValue(), loginPassword.getValue());

		boolean result = UserManager.getInstance().login(user);
		
		if(result){
			navigator.navigateTo(Views.USER_PAGE);
		}else{
			Notification.show("Error", "The User Email or Password is incorrect", Type.ERROR_MESSAGE);
		}

	}

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("get in login");

	}

}

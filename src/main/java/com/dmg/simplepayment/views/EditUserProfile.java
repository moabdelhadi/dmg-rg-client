package com.dmg.simplepayment.views;

import java.util.Calendar;

import com.dmg.core.exception.DataAccessLayerException;
import com.dmg.core.persistence.FacadeFactory;
import com.dmg.simplepayment.beans.UserAccount;
import com.dmg.simplepayment.beans.UserStatus;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class EditUserProfile extends VerticalLayout implements View {

	private Navigator navigator;
	private Button registerButton;
	private TextField  loginEmail;
	private TextField  name;
	private TextField  poBox;
	private TextField  poBoxCity;
	private PasswordField  newPassword;
	private PasswordField  confirmPassword;
	private TextField  city;
	private TextField  appartmentNumber;
	private TextField  buildingNumber;
	private TextField  contractNo;
	private TextField  phone;
	private TextField  mobile;
	private TextField  status;
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
		customLayout.addComponent(loginEmail, "userEmail");
		
		name = new TextField("Full Name");
		name.setInputPrompt("Full Name");
		customLayout.addComponent(name, "fullName");
		
		poBox = new TextField("POBOX");
		poBox.setInputPrompt("POBOX");
		customLayout.addComponent(poBox, "poBox");
		
		poBoxCity = new TextField("POBOX CITY");
		poBoxCity.setInputPrompt("POBOX CITY");
		customLayout.addComponent(poBoxCity, "poBoxCity");

		newPassword = new PasswordField("New Password");
		newPassword.setInputPrompt("New Password");
		customLayout.addComponent(newPassword, "newPassword");
		
		confirmPassword = new PasswordField("Confirm Password");
		confirmPassword.setInputPrompt("Confirm Password");
		customLayout.addComponent(confirmPassword, "confirmPassword");
		
		city = new TextField("City");
		city.setInputPrompt("City");
		customLayout.addComponent(city, "city");
		
		appartmentNumber = new TextField("Appartment No.");
		appartmentNumber.setInputPrompt("Appartment No.");
		customLayout.addComponent(appartmentNumber, "appartmentNumber");
		
		buildingNumber = new TextField("Building Number");
		buildingNumber.setInputPrompt("Building Number");
		customLayout.addComponent(buildingNumber, "buildingNumber");
		
		contractNo = new TextField("Account No.");
		contractNo.setInputPrompt("Account No.");
		customLayout.addComponent(contractNo, "accountId");
		
		phone = new TextField("Phone No.");
		phone.setInputPrompt("Phone No.");
		customLayout.addComponent(phone, "phone");
		
		mobile = new TextField("Mobile No.");
		mobile.setInputPrompt("Mobile No.");
		customLayout.addComponent(mobile, "mobile");
		

		status = new TextField("Status");
		status.setInputPrompt("Status");
		customLayout.addComponent(status, "status");
		
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
		
		if(user==null){
			//TODO Log ERROR if user Null
			return ;
		}
		
		user.setName(name.getValue());
		
		user.setStatus(UserStatus.NEW.value());
		user.setPobox(poBox.getValue());
		user.setPoboxCity(poBoxCity.getValue());
		
		//TODO setactivationString
		user.setActivationString("avtivationString");
		user.setEmail(loginEmail.getValue());
		user.setLastUpdate(Calendar.getInstance().getTime());
		user.setPassword(newPassword.getValue());
		user.setPhone(phone.getValue());
		user.setUpdateDate(Calendar.getInstance().getTime());
		
		try {
			FacadeFactory.getFacade().store(user);
			navigator.navigateTo(Views.ACTIVATION_PAGE);
		} catch (DataAccessLayerException e) {
			navigator.navigateTo(Views.EDIT_PROFILE_PAGE);
		}
		
		

	}


	private boolean vaildateRegister() {

		boolean status = true;
		resetFormValidation();

		try {
			contractNo.validate();
		} catch (InvalidValueException e) {
			contractNo.setComponentError(new UserError("This Field is required"));
			status = false;
		}

		try {
			newPassword.validate();
		} catch (InvalidValueException e) {
			status = false;
		}
		
		try {
			confirmPassword.validate();
		} catch (InvalidValueException e) {
			status = false;
		}
		
		if (newPassword.getValue() != null && !newPassword.getValue().trim().isEmpty() && confirmPassword != null ){
			if (!newPassword.getValue().equals(confirmPassword.getValue())) {
				confirmPassword.setComponentError(new UserError("Password must match"));
				status= false;
			}
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
		status.setComponentError(null);

	}



	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("get in login");

	}

}

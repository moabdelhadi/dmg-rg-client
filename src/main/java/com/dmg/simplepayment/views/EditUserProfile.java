package com.dmg.simplepayment.views;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import com.dmg.client.auth.SessionHandler;
import com.dmg.core.exception.DataAccessLayerException;
import com.dmg.core.persistence.FacadeFactory;
import com.dmg.simplepayment.beans.UserAccount;
import com.dmg.simplepayment.beans.UserStatus;
import com.dmg.util.Logger;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
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
import com.vaadin.ui.HorizontalLayout;
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
	private TextField phonePrefix;
	private TextField mobile;
	private TextField mobilePrefix;
	// private TextField status;
	private UserAccount user;
	private boolean edit;

	public EditUserProfile(Navigator navigator) {
		this.navigator = navigator;
		init();
	}

	private void init() {

		setSizeFull();
		
		HorizontalLayout hsplit = new HorizontalLayout();
		CustomLayout optionLayout = createOptionLayout();
		hsplit.addComponent(optionLayout);

		
		CustomLayout customLayout = new CustomLayout("register");
		customLayout.setWidth("750px");
//		customLayout.setHeight("760px");
		// customLayout.setWidth("20%");
		loginEmail = new TextField("User Email");
		loginEmail.setInputPrompt("User Email");
		loginEmail.setRequired(true);
		loginEmail.setHeight("30px");
		loginEmail.setStyleName("h2");
		loginEmail.setRequiredError("This field is required");
		loginEmail.addValidator(new EmailValidator("Please enter Valid Email"));
		customLayout.addComponent(loginEmail, "userEmail");

		name = new TextField("Full Name");
		name.setInputPrompt("Full Name");
		name.setEnabled(false);
		name.setRequired(true);
		name.setHeight("30px");
		name.setStyleName("h2");
		name.setRequiredError("This field is required");
		customLayout.addComponent(name, "fullName");

		poBox = new TextField("POBOX");
		poBox.setInputPrompt("POBOX");
		poBox.setHeight("30px");
		poBox.setStyleName("h2");
		customLayout.addComponent(poBox, "poBox");

		poBoxCity = new TextField("POBOX CITY");
		poBoxCity.setInputPrompt("POBOX CITY");
		poBoxCity.setHeight("30px");
		poBoxCity.setStyleName("h2");
		customLayout.addComponent(poBoxCity, "poBoxCity");

		newPassword = new PasswordField("New Password");
		newPassword.setInputPrompt("New Password");
		newPassword.setRequired(true);
		newPassword.setHeight("30px");
		newPassword.setStyleName("h2");
		newPassword.setRequiredError("This field is required");
		customLayout.addComponent(newPassword, "newPassword");

		confirmPassword = new PasswordField("Confirm Password");
		confirmPassword.setInputPrompt("Confirm Password");
		confirmPassword.setRequired(true);
		confirmPassword.setHeight("30px");
		confirmPassword.setStyleName("h2");
		confirmPassword.setRequiredError("This field is required");

		customLayout.addComponent(confirmPassword, "confirmPassword");

		city = new Label();
		city.setHeight("30px");
		city.setStyleName("h2");
		customLayout.addComponent(city, "city");

		appartmentNumber = new Label();
		appartmentNumber.setHeight("30px");
		appartmentNumber.setStyleName("h2");
		customLayout.addComponent(appartmentNumber, "appartmentNumber");

		buildingNumber = new Label("Building Number: 55");
		buildingNumber.setHeight("30px");
		buildingNumber.setStyleName("h2");
		customLayout.addComponent(buildingNumber, "buildingNumber");

		contractNo = new Label("Account No.: 123");
		contractNo.setHeight("30px");
		contractNo.setStyleName("h2");
		customLayout.addComponent(contractNo, "accountId");

		phonePrefix = new TextField("Phone No.");
		phonePrefix.setInputPrompt("04");
		phonePrefix.setHeight("30px");
		phonePrefix.setStyleName("h2");
		customLayout.addComponent(phonePrefix, "phonePrefix");
		
		phone = new TextField("");
		phone.setInputPrompt("1234567");
		phone.setHeight("30px");
		phone.setStyleName("h2");
		customLayout.addComponent(phone, "phone");

		mobilePrefix = new TextField("Mobile No.");
		mobilePrefix.setInputPrompt("050");
		mobilePrefix.setRequired(true);
		mobilePrefix.setHeight("30px");
		mobilePrefix.setStyleName("h2");
		mobilePrefix.setRequiredError("This field is required");
		customLayout.addComponent(mobilePrefix, "mobilePrefix");

		
		mobile = new TextField("");
		mobile.setInputPrompt("1234567");
		mobile.setRequired(true);
		mobile.setHeight("30px");
		mobile.setStyleName("h2");
		mobile.setRequiredError("This field is required");
		mobile.setMaxLength(7);
		customLayout.addComponent(mobile, "mobile");

		registerButton = new Button("Register");
		// loginButton.addStyleName(Runo.BUTTON_BIG);
		// loginButton.setClickShortcut(KeyCode.ENTER);
		customLayout.addComponent(registerButton, "registerButton");

		
		hsplit.addComponent(customLayout);
		addComponent(hsplit);

		registerButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				registerNewUser();
			}
		});

	}
	
	
	private CustomLayout createOptionLayout() {
		
		CustomLayout customLayout = new CustomLayout("options");
		customLayout.setWidth("188px");
		customLayout.setStyleName("optionLayout");
		
		Button summary = new Button("Account Summary");
		summary.addStyleName("optViewButton");
//		summary.setHeight("75px");
		//summary.setIcon(new ThemeResource("img/blueButton.png"), "Account Summary");
		
		
		Button editProfile = new Button("Edit Proifile");
//		editProfile.setHeight("75px");
		editProfile.addStyleName("optViewButton");
		
		
		Button changePassword = new Button("Change Password");
//		changePassword.setHeight("75px");
		changePassword.addStyleName("optViewButton");
		
		customLayout.addComponent(summary, "summary");
		customLayout.addComponent(editProfile, "edit_profile");
		customLayout.addComponent(changePassword, "change_password");
		
		summary.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Views.USER_PAGE);
			}
		});
		
		editProfile.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Views.EDIT_PROFILE_PAGE);
			}
		});
		
		changePassword.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Views.CHANGE_PASSWORD);
			}
		});
		
		return customLayout;
	}

	private void registerNewUser() {

		if (!vaildateRegister()) {
			return;
		}

		if (user == null) {
			Logger.error(this, "There is no User Values");
			return;
		}

		//user.setName(name.getValue());
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
			//navigator.navigateTo(Views.EDIT_PROFILE_PAGE+"/"+user.getContractNo()+"/"+user.getCity());
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

//		try {
//			name.validate();
//		} catch (InvalidValueException e) {
//			String htmlMessage = e.getHtmlMessage();
//			name.setComponentError(new UserError(htmlMessage, ContentMode.HTML, ErrorLevel.ERROR));
//			status = false;
//		}

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
		
		try{
			if(phone!=null && phone.getValue()!=null && !phone.getValue().isEmpty()){
				if(phone.getValue().trim().length()!=7){
					phone.setComponentError(new UserError("Invalid Phone Number - lenght", ContentMode.HTML, ErrorLevel.ERROR));
					status = false;
				}
				Integer.parseInt(phone.getValue());
			}
		}catch(Exception e){
			phone.setComponentError(new UserError("Invalid Phone Number - digits", ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}
		
		try {
			phonePrefix.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			phonePrefix.setComponentError(new UserError(htmlMessage, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}
		
		try{
			if(phonePrefix!=null && phonePrefix.getValue()!=null && !phonePrefix.getValue().isEmpty()){
				if(phonePrefix.getValue().trim().length()!=2){
					phonePrefix.setComponentError(new UserError("Invalid phonePrefix Number - lenght", ContentMode.HTML, ErrorLevel.ERROR));
					status = false;
				}
				Integer.parseInt(phonePrefix.getValue());
			}
		}catch(Exception e){
			phonePrefix.setComponentError(new UserError("Invalid phonePrefix Number - digits", ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		try {
			mobile.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			mobile.setComponentError(new UserError(htmlMessage, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}
		
		try{
			if(mobile!=null && mobile.getValue()!=null && !mobile.getValue().isEmpty()){
				if(mobile.getValue().trim().length()!=7){
					mobile.setComponentError(new UserError("Invalid mobile Number - lenght", ContentMode.HTML, ErrorLevel.ERROR));
					status = false;
				}
				Integer.parseInt(mobile.getValue());
			}
		}catch(Exception e){
			mobile.setComponentError(new UserError("Invalid mobile Number - digits", ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}
		
		try {
			mobilePrefix.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			mobilePrefix.setComponentError(new UserError(htmlMessage, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}
		
		try{
			if(mobilePrefix!=null && mobilePrefix.getValue()!=null && !mobilePrefix.getValue().isEmpty()){
				if(mobilePrefix.getValue().trim().length()!=3){
					mobilePrefix.setComponentError(new UserError("Invalid mobilePrefix Number - lenght", ContentMode.HTML, ErrorLevel.ERROR));
					status = false;
				}
				Integer.parseInt(mobilePrefix.getValue());
			}
		}catch(Exception e){
			mobilePrefix.setComponentError(new UserError("Invalid mobilePrefix Number - digits", ContentMode.HTML, ErrorLevel.ERROR));
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
		phonePrefix.setComponentError(null);
		mobile.setComponentError(null);
		mobilePrefix.setComponentError(null);
		
		// status.setComponentError(null);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		
		Logger.debug(this, "Get in Edit User Profile Entree");
		
		UserAccount accountFromAccountID = null;
		accountFromAccountID = getuserFromParam(event.getParameters());
		
		if(accountFromAccountID!=null){
			Logger.info(this, "Find User From Params");
			user = accountFromAccountID;
			setEdit(false);
		}else{
			Logger.info(this, "No User From Params , try to find from session");
			accountFromAccountID = getUserFromSession();
			if(accountFromAccountID !=null){
				user = accountFromAccountID;
				setEdit(true);
				Logger.info(this, "Find User From Session");
			}
		}
		

		if(accountFromAccountID==null){
			Logger.error(this,"No User Available");
			navigator.navigateTo(Views.LOGIN);
			return ;
		}
		setUserValues();
		resetFormValidation();

	}
	
	
	

	private UserAccount getUserFromSession() {
		
		UserAccount userAccount = SessionHandler.get();
		UserAccount accountFromAccountID= UserManager.getInstance().getAccountFromAccountID(userAccount);
		return accountFromAccountID;
	}




	private UserAccount getuserFromParam(String parametersString) {
		
		
		Logger.debug(this, "Parameters=" + parametersString);

		String[] parameters = parametersString.split("/");

		if (parameters == null || parameters.length != 2) {
			Logger.error(this, "No Paratemeres Passed to this user or Parameters are error ");
			return null;
		}

		if (StringUtils.isEmpty(parameters[0]) || StringUtils.isEmpty(parameters[1])) {
			Logger.error(this, "Paratemeres Value is in correct " + parameters[0] + " , " + parameters[1]);
			return null;
		}

		UserAccount userAccount = new UserAccount();
		userAccount.setContractNo(parameters[0]);
		userAccount.setCity(parameters[1]);
		UserAccount accountFromAccountID = UserManager.getInstance().getAccountFromAccountID(userAccount);
		return accountFromAccountID;
		
	}

	private void setUserValues() {

		city.setValue("");
		if (user.getCity() != null) {
			city.setValue(user.getCity());
		}

		appartmentNumber.setValue("");
		if (user.getAppartmentNumber() != null) {
			appartmentNumber.setValue(user.getAppartmentNumber());
		}
		buildingNumber.setValue("");
		if (user.getBuildingNumber() != null) {
			buildingNumber.setValue(user.getBuildingNumber());
		}

		contractNo.setValue("");
		if (user.getContractNo() != null) {
			contractNo.setValue(user.getContractNo());
		}

		name.setValue("");
		if (user.getName() != null) {
			name.setValue(user.getName());
		}

		loginEmail.setValue("");
		if (user.getEmail() != null) {
			loginEmail.setValue(user.getEmail());
		}

		poBox.setValue("");
		if (user.getPobox() != null) {
			poBox.setValue(user.getPobox());
		}

		poBoxCity.setValue("");
		if (user.getPoboxCity() != null) {
			poBoxCity.setValue(user.getPoboxCity());
		}

		phone.setValue("");
		phonePrefix.setValue("");
		if (user.getPhone() != null) {
			String[] split = user.getPhone().split("/");
			if(split.length==2){
				phonePrefix.setValue(split[0]);
				phone.setValue(split[1]);
			}
			
		}

		mobile.setValue("");
		mobilePrefix.setValue("");
		if (user.getMobile() != null) {
			String[] split = user.getMobile().split("/");
			if(split.length==2){
				mobilePrefix.setValue(split[0]);
				mobile.setValue(split[1]);
			}
		}

	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	
}

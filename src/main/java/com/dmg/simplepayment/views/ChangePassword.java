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

/** 
 * 
 * @author mabdelhadi
 *
 */

public class ChangePassword extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	private Navigator navigator;
	private Button saveButtun;
	private TextField name;
	private PasswordField oldPassword;
	private PasswordField newPassword;
	private PasswordField confirmPassword;
	private Label city;
	private Label appartmentNumber;
	private Label buildingNumber;
	private Label contractNo;
	private UserAccount user;
	
	public ChangePassword(Navigator navigator) {
		this.navigator = navigator;
		init();
	}

	private void init() {

		setSizeFull();
		
		HorizontalLayout hsplit = new HorizontalLayout();
		CustomLayout optionLayout = createOptionLayout();
		hsplit.addComponent(optionLayout);

		
		CustomLayout customLayout = new CustomLayout("changePassword");

		name = new TextField("Full Name");
		name.setInputPrompt("Full Name");
		name.setEnabled(false);
		name.setRequired(true);
		name.setHeight("30px");
		name.setStyleName("h2");
		name.setRequiredError("This field is required");
		customLayout.addComponent(name, "fullName");

		oldPassword = new PasswordField("Old Password");
		oldPassword.setInputPrompt("Old Password");
		oldPassword.setRequired(true);
		oldPassword.setHeight("30px");
		oldPassword.setStyleName("h2");
		oldPassword.setRequiredError("This field is required");
		customLayout.addComponent(oldPassword, "oldPassword");

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

		saveButtun = new Button("Save");

		customLayout.addComponent(saveButtun, "saveButtun");

		
		hsplit.addComponent(customLayout);
		addComponent(hsplit);

		saveButtun.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				changePassword();
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

	private void changePassword() {

		if (!vaildateRegister()) {
			return;
		}

		if (user == null) {
			Logger.error(this, "There is no User Values");
			return;
		}

		//user.setName(name.getValue());
		user.setStatus(UserStatus.ACTIVE.value());
		
		String password = user.getPassword();
		if(!StringUtils.equals(password, oldPassword.getValue())){
			Logger.error(this, "Old Password is not correct password=" + password + "old password="+ oldPassword.getValue());
			Notification.show("ERROR", "Old Password is not correct", Notification.Type.HUMANIZED_MESSAGE);
			return;
		}

		// TODO setactivationString
		user.setActivationString("");
		user.setLastUpdate(Calendar.getInstance().getTime());
		user.setPassword(newPassword.getValue());
		user.setUpdateDate(Calendar.getInstance().getTime());

		try {
			FacadeFactory.getFacade().store(user);
			navigator.navigateTo(Views.USER_PAGE);
		} catch (DataAccessLayerException e) {
			Logger.error(this, "Old Password is not correct password=" + password + "old password="+ oldPassword.getValue());
			Notification.show("ERROR", "Error in save the user passsword", Notification.Type.HUMANIZED_MESSAGE);
		}

	}

	private boolean vaildateRegister() {

		boolean status = true;
		resetFormValidation();

		try {
			oldPassword.validate();
		} catch (InvalidValueException e) {
			String htmlMessage = e.getHtmlMessage();
			oldPassword.setComponentError(new UserError(htmlMessage, ContentMode.HTML, ErrorLevel.ERROR));
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

		return status;

	}

	private void resetFormValidation() {

		oldPassword.setComponentError(null);
		newPassword.setComponentError(null);
		confirmPassword.setComponentError(null);
		contractNo.setComponentError(null);
		appartmentNumber.setComponentError(null);
		buildingNumber.setComponentError(null);
		city.setComponentError(null);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		
		Logger.debug(this, "Get in Edit User Profile Entree");
		UserAccount accountFromAccountID = getUserFromSession();
		this.user = accountFromAccountID;
		
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

	}

	
}

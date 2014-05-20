package com.dmg.simplepayment.views;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dmg.core.exception.DataAccessLayerException;
import com.dmg.core.persistence.FacadeFactory;
import com.dmg.simplepayment.beans.Constants;
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
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class WelcomeAfterRegister extends VerticalLayout implements View {

	private Navigator navigator;

	private TextField  accountId;
	private ComboBox citySelect;
	private TextField  activationCode;

	private Button activateButton;

	public WelcomeAfterRegister(Navigator navigator) {
		this.navigator = navigator;
		init();
	}

	private void init() {

		setSizeFull();
		CustomLayout customLayout = new CustomLayout("ActivationPage");
		
		accountId = new TextField("Account No.");
		accountId.setInputPrompt("Account No.");
		customLayout.addComponent(accountId, "accountId");
		
		activationCode = new TextField("Activation Code");
		activationCode.setInputPrompt("Activation Code");
		customLayout.addComponent(activationCode, "activationCode");
		
		citySelect = new ComboBox("City");
		citySelect.addItem("DUBAI");
		citySelect.addItem("ABUDHABI");
		citySelect.setInputPrompt("City");
		customLayout.addComponent(citySelect, "city");

		
		
		activateButton = new Button("Activate");
		// loginButton.addStyleName(Runo.BUTTON_BIG);
		// loginButton.setClickShortcut(KeyCode.ENTER);
		customLayout.addComponent(activateButton, "activateButton");
		

		addComponent(customLayout);

		activateButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				activateUserAccount();
			}
		});

	}

	private void activateUserAccount() {
		
		try{
			if (!vaildateRegister()) {
		
			return;
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>(); 
		parameters.put(Constants.USER_ACCOUNT_ID, accountId.getValue());
		parameters.put(Constants.USER_CITY, citySelect.getValue());
		
		List<UserAccount> list = FacadeFactory.getFacade().list(UserAccount.class, parameters);
		
		if(list==null || list.isEmpty()){
			//TODO Log ERROR if user Null
			return ;
		}
		
		if(list.size()>1){
			//TODO log worning
		}
		
		UserAccount userAccount = list.get(0);
		String activationString = userAccount.getActivationString();
		
		if(activationString==null || activationString.trim().isEmpty()){
			//log please register first
			return;
		}
		
		if(!activationString.equals(activationCode.getValue())){
			//log invaled Activation Code
			return;
		}
		
		userAccount.setActivationString("");
		userAccount.setStatus(UserStatus.ACTIVE.value());
		userAccount.setLastUpdate(Calendar.getInstance().getTime());
		FacadeFactory.getFacade().store(userAccount);
		}catch(DataAccessLayerException e){
			//log Error
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

		return status;

	}



	private void resetFormValidation() {

		accountId.setComponentError(null);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("get in login");

	}

}

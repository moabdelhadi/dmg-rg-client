package com.dmg.client.simplepayment.views;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.user.UserManager;
import com.dmg.core.bean.Constants;
import com.dmg.core.bean.UserAccount;
import com.dmg.core.bean.UserStatus;
import com.dmg.core.exception.DataAccessLayerException;
import com.dmg.util.PropertiesManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

public class ActivationView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(ActivationView.class);

	private final Navigator navigator;

	private Link backToLogin;
	private Label activationLabel;
	private UserAccount userAccount;
	

	public ActivationView(Navigator navigator) {
		this.navigator = navigator;
		init();
	}

	private void init() {

		setSizeFull();
		CustomLayout customLayout = new CustomLayout("ActivationPage");

		activationLabel = new Label("Please check your email for activation link");

		customLayout.addComponent(activationLabel, "activationMsg");

		String loginUrl = PropertiesManager.getInstance().getProperty("payment.home.path");
		backToLogin = new Link("Go to login", new ExternalResource(loginUrl));
		backToLogin.addStyleName(Runo.BUTTON_BIG);
		customLayout.addComponent(backToLogin, "backToLogin");

		addComponent(customLayout);

//		backToLogin.addClickListener(new ClickListener() {
//
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//				navigator.navigateTo(Views.LOGIN);
//			}
//		});

	}

	@Override
	public void enter(ViewChangeEvent event) {
		
		String parameters = event.getParameters();
		if ("".equals(parameters) || parameters==null) {
			return;
		}
		log.debug("Activation Pams="+parameters);
		String[] params = parameters.split("/");
		if (params.length > 2) {
			String key = params[0];
			String city = params[1];
			String accountNo = params[2];
			
			if(params.length>3){
				for(int i=3; i <params.length ; i++){
					accountNo+= "/" + params[i];
				}
			}
			
			
			try {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put(Constants.USER_ACCOUNT_ID, accountNo);
				paramMap.put(Constants.USER_CITY, city);
				paramMap.put(Constants.ACTIVATION_KEY, key);
				userAccount = UserManager.getInstance().validateAccount(paramMap);
				userAccount.setStatus(UserStatus.ACTIVE.value());
				userAccount.setActivationKey("");
				userAccount.setSyncStatus(2);
				UserManager.getInstance().updateAccount(userAccount);
				Notification.show("Success", "Your account is activated", Type.WARNING_MESSAGE);
				
//				navigator.navigateTo(Views.LOGIN);

			} catch (DataAccessLayerException e) {
				Notification.show("Error", "Link is invalid", Type.ERROR_MESSAGE);
//				navigator.navigateTo(Views.LOGIN);
			}

		} else {
			Notification.show("Error", "URL is malformed", Type.ERROR_MESSAGE);
			navigator.navigateTo(Views.LOGIN);
		}
	}

}

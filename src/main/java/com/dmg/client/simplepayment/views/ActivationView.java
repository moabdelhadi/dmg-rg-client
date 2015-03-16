package com.dmg.client.simplepayment.views;

import java.util.HashMap;
import java.util.Map;

import com.dmg.client.simplepayment.beans.Constants;
import com.dmg.client.simplepayment.beans.UserAccount;
import com.dmg.client.simplepayment.beans.UserStatus;
import com.dmg.client.user.UserManager;
import com.dmg.core.exception.DataAccessLayerException;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

public class ActivationView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Navigator navigator;

	private Button backToLogin;
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

		backToLogin = new Button("Back to login");
		backToLogin.addStyleName(Runo.BUTTON_BIG);
		customLayout.addComponent(backToLogin, "backToLogin");

		addComponent(customLayout);

		backToLogin.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Views.LOGIN);
			}
		});

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
				paramMap.put(Constants.ACTIVATION_KEY, key);
				userAccount = UserManager.getInstance().validateAccount(paramMap);
				userAccount.setStatus(UserStatus.ACTIVE.value());
				userAccount.setActivationKey("");
				userAccount.setSyncStatus(2);
				UserManager.getInstance().updateAccount(userAccount);
				Notification.show("Success", "Your account is activated", Type.HUMANIZED_MESSAGE);

				navigator.navigateTo(Views.LOGIN);
			} catch (DataAccessLayerException e) {
				Notification.show("Error", "Link is invalid", Type.ERROR_MESSAGE);
				navigator.navigateTo(Views.LOGIN);
			}

		} else {
			Notification.show("Error", "URL is malformed", Type.ERROR_MESSAGE);
			navigator.navigateTo(Views.LOGIN);
		}
	}

}

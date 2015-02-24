package com.dmg.client.simplepayment.views;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.auth.SessionHandler;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomLayout;

public class AccountOptions implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static AccountOptions INSTANCE;
	private Navigator navigator;
	private static final Logger logger = LoggerFactory.getLogger(AccountOptions.class);

	public AccountOptions(Navigator navigator) {
		
		this.navigator = navigator;

	}

//	public static AccountOptions getInstance(Navigator navigator) {
//		
//		
//		if (INSTANCE != null) {
//			return INSTANCE;
//		}
//		synchronized (AccountOptions.class){
//			if (INSTANCE == null) {
//				INSTANCE = new AccountOptions(navigator);
//			}
//		}
//		return INSTANCE;
//	
//	}

	public CustomLayout createOptionLayout() {

		CustomLayout customLayout = new CustomLayout("options");
		customLayout.setWidth("188px");
		customLayout.setStyleName("optionLayout");

		Button summary = new Button("Account Overview");
		summary.addStyleName("optViewButton");

		Button editProfile = new Button("Edit Proifile");
		editProfile.addStyleName("optViewButton");

		Button changePassword = new Button("Change Password");
		changePassword.addStyleName("optViewButton");

		Button logoutUser = new Button("Logout");
		logoutUser.addStyleName("optViewButton");

		customLayout.addComponent(summary, "summary");
		customLayout.addComponent(editProfile, "edit_profile");
		customLayout.addComponent(changePassword, "change_password");
		customLayout.addComponent(logoutUser, "logout_user");

		summary.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				logger.debug("USER Page Button Clicked");
				navigator.navigateTo(Views.USER_PAGE);
			}
		});

		editProfile.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				logger.debug("Edit Profile button clicked");
				navigator.navigateTo(Views.EDIT_PROFILE_PAGE);
			}
		});

		changePassword.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				logger.debug("Change Password button clicked");
				navigator.navigateTo(Views.CHANGE_PASSWORD);
			}
		});
		
		
		logoutUser.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				logger.debug("Logout button clicked");
				SessionHandler.logout();
				navigator.navigateTo(Views.LOGIN);
			}
		});

		return customLayout;
	}

}

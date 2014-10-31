package com.dmg.simplepayment.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class AccountOptions {

	private static AccountOptions INSTANCE;
	private Navigator navigator;

	private AccountOptions(Navigator navigator) {
		
		this.navigator = navigator;

	}

	public static AccountOptions getInstance(Navigator navigator) {
		if (INSTANCE == null) {
			INSTANCE = new AccountOptions(navigator);
		}
		return INSTANCE;
	}

	public CustomLayout createOptionLayout() {

		CustomLayout customLayout = new CustomLayout("options");
		customLayout.setWidth("188px");
		customLayout.setStyleName("optionLayout");

		Button summary = new Button("Account Summary");
		summary.addStyleName("optViewButton");
		// summary.setHeight("75px");
		// summary.setIcon(new ThemeResource("img/blueButton.png"),
		// "Account Summary");

		Button editProfile = new Button("Edit Proifile");
		// editProfile.setHeight("75px");
		editProfile.addStyleName("optViewButton");

		Button changePassword = new Button("Change Password");
		// changePassword.setHeight("75px");
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

}

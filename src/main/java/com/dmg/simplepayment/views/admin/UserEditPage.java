package com.dmg.simplepayment.views.admin;

import com.dmg.simplepayment.views.Views;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class UserEditPage extends HorizontalLayout implements View {
	

	private FormLayout form = new FormLayout();

	/** Login Fileds **/
	private Label name;
	private Button action;

	private Navigator navigator;

	public UserEditPage(Navigator navigator) {
		this.navigator = navigator;
		setWidth(600, Unit.PIXELS);
		init();
	}


	private void init() {

		name = new Label("User Page Page, it would be List user profile and the latest three bells");
		action = new Button("go to Login page");
		action.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("user Page Actions");
				navigator.navigateTo(Views.LOGIN);
			}
		});

		form.setMargin(new MarginInfo(true, false, false, true));
		form.addComponent(name);
		form.addComponent(action);
		addComponent(form);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		
		
	}
	
	
}

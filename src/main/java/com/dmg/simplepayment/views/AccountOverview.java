package com.dmg.simplepayment.views;

import java.util.ArrayList;
import java.util.List;

import com.dmg.simplepayment.beans.BellPrife;
import com.dmg.simplepayment.beans.UserAccount;
import com.dmg.simplepayment.beans.UserStatus;
import com.dmg.util.EncryptionUtil;
import com.dmg.util.Logger;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
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
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AccountOverview extends VerticalLayout implements View {

	private Navigator navigator;

	/** Login Fileds **/
	private Button payButton;

	public AccountOverview(Navigator navigator) {
		this.navigator = navigator;
		init();
	}

	private void init() {

		setSizeFull();
		CustomLayout customLayout = new CustomLayout("AccountOverview");
		// customLayout.setWidth("20%");
		
		Label welcomeName = new Label("Welcome Husain");
		welcomeName.setStyleName("h1");
		customLayout.addComponent(welcomeName, "welcomeName");
		

		
		List<BellPrife> list = new ArrayList<BellPrife>();
		list.add(new BellPrife("1", "May 01, 2014", "100"));
		list.add(new BellPrife("2", "May 02, 2014", "200"));
		list.add(new BellPrife("3", "May 03, 2014", "300"));
		
		int counter =0;
		for (BellPrife bill : list) {
			
			Label date = new Label(bill.getDate());
			customLayout.addComponent(date, "date_"+counter);

			Label amount = new Label(bill.getAmount());
			customLayout.addComponent(amount, "amount_"+counter);

			Label link = new Label(bill.getId());
			customLayout.addComponent(link, "view_"+counter);
			
			counter++;
			
		}
		

		Label totalAmount = new Label("Ammount Due date: "+ 500 +" AED");
		customLayout.addComponent(totalAmount, "totalAmount");

		//payButton
		
		
		payButton = new Button("Pay Online");
		payButton.addStyleName(Runo.BUTTON_BIG);
		customLayout.addComponent(payButton, "payButton");


		addComponent(customLayout);

		payButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Logger.info(this, "Pay on process");
			}
		});

	}


	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("get in account overview");

	}

}

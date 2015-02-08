package com.dmg.client.simplepayment.views;

import com.dmg.client.auth.util.ValidateMessage;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.server.AbstractErrorMessage.ContentMode;
import com.vaadin.server.ErrorMessage.ErrorLevel;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Runo;

/**
 * This Should be a pop up to enter the email
 * 
 * @author mabdelhadi
 * 
 */
public class ForgetPassword extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TextField accountNumberField;
	private Button resetPasswordButton;
	private ComboBox citySelect;
	private Notification notification;

	/** Login Fileds **/

	private final Navigator navigator;

	public ForgetPassword(Navigator navigator) {
		// this.fragmentAndParameters = null;
		this.navigator = navigator;
		init();
	}

	public ForgetPassword(Navigator navigator, String fragmentAndParameters) {
		// this.fragmentAndParameters = fragmentAndParameters;
		this.navigator = navigator;
		init();
	}

	private void init() {

		notification = new Notification("Email Sent Successfully", "Please check your email to reset your password", Notification.Type.HUMANIZED_MESSAGE);
		notification.setDelayMsec(5000);

		CustomLayout layout = new CustomLayout("forgotPassword");

		citySelect = new ComboBox("Region");
		citySelect.setHeight("30px");
		// loginCitySelect.setWidth("200px");
		citySelect.setStyleName("h2");
		citySelect.setCaption("Region");
		citySelect.setRequired(true);
		citySelect.setRequiredError("Please select your region");
		citySelect.setNullSelectionAllowed(false);
		citySelect.addItem("DUBAI");
		citySelect.addItem("ABUDHABI");
		citySelect.setItemCaption("DUBAI", "Dubai & Northern Emirates");
		citySelect.setItemCaption("ABUDHABI", "Abu dhabi, Alain & Western Region");
		citySelect.setInputPrompt("Please select your region");
		citySelect.setTextInputAllowed(false);
		layout.addComponent(citySelect, "userCity");

		accountNumberField = new TextField("Account No.");
		accountNumberField.setHeight("30px");
		accountNumberField.setStyleName("h2");
		accountNumberField.setInputPrompt("Please enter you account no. as shown in the help button");
		accountNumberField.setRequired(true);
		accountNumberField.setRequiredError("Please enter account number");
		// loginAccountId.setWidth("200px");
		layout.addComponent(accountNumberField, "accountNumber");

		resetPasswordButton = new Button("Reset Password");
		resetPasswordButton.addStyleName(Runo.BUTTON_BIG);
		resetPasswordButton.setDisableOnClick(true);
		resetPasswordButton.setClickShortcut(KeyCode.ENTER);
		layout.addComponent(resetPasswordButton, "resetPassword");

		resetPasswordButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1410371220573383927L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (validate()) {
					notification.show(Page.getCurrent());
					navigator.navigateTo(Views.LOGIN);
				}
				resetPasswordButton.setEnabled(true);

			}
		});

		addComponent(layout);

	}

	private boolean validate() {

		boolean status = true;
		resetFormValidation();

		try {
			accountNumberField.validate();
		} catch (InvalidValueException e) {
			accountNumberField.setComponentError(new UserError(null, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}
		try {
			citySelect.validate();
		} catch (InvalidValueException e) {
			citySelect.setComponentError(new UserError(null, ContentMode.HTML, ErrorLevel.ERROR));
			status = false;
		}

		if (status) {
			ValidateMessage validateMessage = UserManager.getInstance().validateAccountAndCity(accountNumberField.getValue(), citySelect.getValue().toString());
			if (!validateMessage.isValid()) {
				status = false;
				Notification.show("Error", validateMessage.getMessage(), Type.ERROR_MESSAGE);
			}
		}
		return status;
	}

	private void resetFormValidation() {
		accountNumberField.setComponentError(null);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}

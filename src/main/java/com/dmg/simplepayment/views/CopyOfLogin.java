package com.dmg.simplepayment.views;

import com.dmg.simplepayment.beans.UserAccount;
import com.dmg.simplepayment.beans.UserStatus;
import com.dmg.util.EncryptionUtil;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class CopyOfLogin extends HorizontalLayout implements View {

	private Navigator navigator;
	private FormLayout loginForm = new FormLayout();

	/** Login Fileds **/
	private TextField loginEmail;
	private PasswordField loginPassword;
	private Button loginButton;
	
	
	private FormLayout registerForm = new FormLayout();
	/** Registration Fileds **/
	private TextField email;
	private PasswordField password;
	private PasswordField confirmPassword;
	private TextField city;
	private TextField buildingNo;
	private TextField appartmentNo;
	private TextField accountNo;
	private TextField name;
	private TextField phone;
	private TextField address;
	private Button button;

	public CopyOfLogin(Navigator navigator) {
		this.navigator = navigator;
		init();
	}

	protected void init() {

		addComponent(loginForm);
		addComponent(registerForm);
		setWidth("600px");

		initLoginForm();
		initRegisterForm();

	}

	private void initRegisterForm() {

		email = new TextField("Email");
		email.addValidator(new EmailValidator("Please Insert Valid Email"));
		email.setRequired(true);
		registerForm.addComponent(email);

		password = new PasswordField("Password");
		password.setRequired(true);
		registerForm.addComponent(password);

		confirmPassword = new PasswordField("confirmPassword");
		confirmPassword.setRequired(true);
		registerForm.addComponent(confirmPassword);

		city = new TextField("city");
		city.setRequired(true);
		registerForm.addComponent(city);

		buildingNo = new TextField("buildingNo");
		buildingNo.setRequired(true);
		registerForm.addComponent(buildingNo);

		appartmentNo = new TextField("appartmentNo");
		appartmentNo.setRequired(true);
		registerForm.addComponent(appartmentNo);

		accountNo = new TextField("accountNo");
		accountNo.setRequired(true);
		registerForm.addComponent(accountNo);

		name = new TextField("name");
		name.setRequired(true);
		registerForm.addComponent(name);

		phone = new TextField("phone");
		phone.setRequired(true);
		registerForm.addComponent(phone);

		address = new TextField("address");
		registerForm.addComponent(address);

		button = new Button("Register");
		registerForm.addComponent(button);

		button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				createNewUser();
			}

		});

	}

	private void createNewUser() {

		System.out.println("Start creation");

		if(!vaildateRegister()){
			return;
		}
		UserAccount user = new UserAccount();
		user.setEmail(email.getValue());
		user.setAccountId(accountNo.getValue());
		user.setAddress(address.getValue());
		user.setAppartmentNumber(appartmentNo.getValue());
		user.setBuildingNumber(buildingNo.getValue());
		user.setCity(city.getValue());
		user.setFirstName(name.getValue());
		user.setPassword(EncryptionUtil.encrypt(password.getValue()));
		user.setPhone(phone.getValue());
		user.setStatus(UserStatus.NEW.value());
		System.out.println(user);
		UserManager.getInstance().registerUser(user);

	}

	
	private boolean vaildateRegister() {

		boolean status = true;
		resetFormValidation();
		
		try{
			email.validate();
		}catch (InvalidValueException e) {
			email.setComponentError(new UserError("This Field is required"));
			status= false;
		}
		
		try{
			password.validate();
		}catch (InvalidValueException e) {
			password.setComponentError(new UserError("This Field is required"));
			status= false;
		}
		
		try{
			confirmPassword.validate();
		}catch (InvalidValueException e) {
			confirmPassword.setComponentError(new UserError("This Field is required"));
			status= false;
		}
		try{
			city.validate();
		}catch (InvalidValueException e) {
			city.setComponentError(new UserError("This Field is required"));
			status= false;
		}
		try{
			buildingNo.validate();
		}catch (InvalidValueException e) {
			buildingNo.setComponentError(new UserError("This Field is required"));
			status= false;
		}
		try{
			appartmentNo.validate();
		}catch (InvalidValueException e) {
			appartmentNo.setComponentError(new UserError("This Field is required"));
			status= false;
		}
		try{
			accountNo.validate();
		}catch (InvalidValueException e) {
			accountNo.setComponentError(new UserError("This Field is required"));
			status= false;
		}
		
		try{
			phone.validate();
		}catch (InvalidValueException e) {
			phone.setComponentError(new UserError("This Field is required"));
			status= false;
		}
		
		try{
			name.validate();
		}catch (InvalidValueException e) {
			name.setComponentError(new UserError("This Field is required"));
			status= false;
		}
		
		
		try{
			address.validate();
		}catch (InvalidValueException e) {
			address.setComponentError(new UserError("PThis Field is required"));
			status= false;
		}
		
		
		if (password.getValue() != null && !password.getValue().trim().isEmpty() && confirmPassword.getValue() != null && !confirmPassword.getValue().trim().isEmpty()){
			if (!password.getValue().equals(confirmPassword.getValue())) {
				password.setComponentError(new UserError("Password must match"));
				status= false;
			}
		}
		return status;

	}
	
	private boolean validateLogin(){
		boolean status = true;
		resetFormValidation();
		
		try{
			loginEmail.validate();
		}catch (InvalidValueException e) {
			loginEmail.setComponentError(new UserError("This Field is required"));
			status= false;
		}
		
		try{
			loginPassword.validate();
		}catch (InvalidValueException e) {
			loginPassword.setComponentError(new UserError("This Field is required"));
			status= false;
		}
		return status;
	}

	private void resetFormValidation() {
		int componentCount = registerForm.getComponentCount();
		for(int i=0 ; i < componentCount ;i++){
			Component component = registerForm.getComponent(i);
			if(component!=null && component instanceof AbstractField){
				AbstractField field = (AbstractField) component;
					field.setComponentError(null);
			}
		}
		
		componentCount = loginForm.getComponentCount();
		for(int i=0 ; i < componentCount ;i++){
			Component component = loginForm.getComponent(i);
			if(component!=null && component instanceof AbstractField){
				AbstractField field = (AbstractField) component;
				field.setComponentError(null);
			}
		}

	}

	
	private void initLoginForm() {

		loginForm.setMargin(new MarginInfo(true, false, false, true));

		loginEmail = new TextField("Email");
		loginEmail.addValidator(new EmailValidator("Please Insert Valid Email"));
		loginEmail.setRequired(true);
		loginForm.addComponent(loginEmail);

		loginPassword = new PasswordField("Password");
		loginPassword.setRequired(true);
		loginForm.addComponent(loginPassword);

		loginButton = new Button("Log In");
		loginButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				loginUser();

			}

		});

		loginForm.addComponent(loginButton);

	}
	
	private void loginUser() {
		
		if(!validateLogin()){
			return;
		}
		String encrypt = EncryptionUtil.encrypt(loginPassword.getValue());
		UserAccount user = new UserAccount(loginEmail.getValue(), encrypt);
		UserManager.getInstance().login(user);

		removeAllComponents();
//		addComponent(new UserPage().getLoginLayout());
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("get in login");
		
	}

}

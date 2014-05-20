package com.dmg.simplepayment.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dmg.core.exception.DataAccessLayerException;
import com.dmg.core.persistence.FacadeFactory;
import com.dmg.simplepayment.beans.Constants;
import com.dmg.simplepayment.beans.UserAccount;
import com.dmg.simplepayment.beans.UserStatus;
import com.dmg.util.EncryptionUtil;
import com.dmg.util.Logger;
import com.dmg.util.PropertiesManager;

public class UserManager {

	private final String CONFIRMATION_BASE_PATH = PropertiesManager.getInstance().getProperty("confirmation_base_path");
	private static final UserManager INSTANCE = new UserManager();

	private UserManager() {
	}

	public static UserManager getInstance() {
		return INSTANCE;
	}

	public boolean login(UserAccount user) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Constants.USER_ACCOUNT_EMAIL, user.getEmail());

		List<UserAccount> list=null;
		try {
			list = FacadeFactory.getFacade().list(UserAccount.class, parameters);
		} catch (DataAccessLayerException e) {
			Logger.error(this, "Email is incorrect", e);
			return false;
		}

		if (list == null) {
			Logger.warn(this, "Email is incorrect");
			return false;
		}

		if (list.size() > 1) {
			Logger.warn(this, "Email is dublicated Please Check");

		}

		UserAccount userAccount = list.get(0);
		String password = userAccount.getPassword();
		if (!StringUtils.isEmpty(user.getPassword()) && user.getPassword().equals(password)) {
			Logger.info(this, "Login Success");
			return true;
		}
		return false;

	}

	public UserAccount getAccountFromAccountID(UserAccount user) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Constants.USER_ACCOUNT_ID, user.getAccountId());
		parameters.put(Constants.USER_CITY, user.getCity());
		List<UserAccount> list;
		try {
			list = FacadeFactory.getFacade().list(UserAccount.class, parameters);
		} catch (DataAccessLayerException e) {
			Logger.error(this, "Account is incorrect", e);
			return null;
		}

		if (list == null) {
			Logger.warn(this, "Email is incorrect");
			return null;
		}

		if (list.size() > 1) {
			Logger.warn(this, "Email is dublicated Please Check");
		}

		UserAccount userAccount = list.get(0);
		return userAccount;

	}

	public void registerUser(UserAccount user) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Constants.USER_ACCOUNT_ID, user.getAccountId());
		parameters.put(Constants.USER_CITY, user.getCity());
		List<UserAccount> list;
		try {
			list = FacadeFactory.getFacade().list(UserAccount.class, parameters);
		} catch (DataAccessLayerException e) {
			Logger.error(this, "Account is incorrect", e);
			return;

		}

		if (list == null) {
			Logger.warn(this, "Account is incorrect");
			return;
		}

		if (list.size() > 1) {
			Logger.warn(this, "Account is dublicated Please Check");
			return;
		}

		UserAccount userAccount = list.get(0);

		String activationLink = user.getEmail() + "==" + System.currentTimeMillis() + "==" + (Math.random() * 10000);
		String encrypt = EncryptionUtil.encrypt(activationLink);
		try {
			String decrypt = EncryptionUtil.decrypt(encrypt);
			if (decrypt != null && decrypt.equals(activationLink)) {
				System.out.println("Encription Success");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		user.setActivationString(encrypt);

		userAccount.setEmail(user.getEmail());
		userAccount.setAddress(user.getEmail());
		userAccount.setFirstName(user.getFirstName());
		userAccount.setLastName(user.getLastName());
		userAccount.setPassword(user.getPassword());
		userAccount.setPhone(user.getPhone());
		userAccount.setStatus(UserStatus.NEW.value());
		
		
		try {
			FacadeFactory.getFacade().store(userAccount);
		} catch (DataAccessLayerException e) {
			Logger.error(this, "Error in saving user acount="+userAccount.getAccountId(), e);
			return;
		}

	}


	public boolean activate(String activationString) {

		Logger.debug(this, "code=" + activationString);
		try {
			UserAccount user = getUserFromActivationLink(activationString);

			if (user != null && activationString.equals(user.getActivationString())) {
				user.setStatus(UserStatus.ACTIVE.value());

				FacadeFactory.getFacade().store(user);

				Logger.info(this, "activation Success");
				return true;
			}

		} catch (Exception e) {
			Logger.error(this, "error in Activate User", e);
			e.printStackTrace();
		}
		return false;

	}

	public boolean resetPassword(String activationString, String password) {

		try {
			UserAccount user = getUserFromActivationLink(activationString);

			if (user != null && activationString.equals(user.getActivationString())) {
				Logger.info(this, "reset enable");
				user.setPassword(password);
				user.setActivationString("");
				FacadeFactory.getFacade().store(user);
				return true;
			}

		} catch (Exception e) {
			Logger.error(this, "error in Activate User", e);
			e.printStackTrace();
		}
		return false;

	}

	private UserAccount getUserFromActivationLink(String string) throws Exception {

		String decrypt = EncryptionUtil.decrypt(string);
		String[] params = decrypt.split("==");

		if (params.length != 3) {
			Logger.warn(this, "invalid activation Link decrypt=" + decrypt);
			return null;
		}

		Logger.debug(this, "params= " + params[0] + " ' " + params[0] + " ' " + params[0]);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Constants.USER_ACCOUNT_EMAIL, params[0].trim());
		
		List<UserAccount> list =FacadeFactory.getFacade().list(UserAccount.class, parameters);

		if (list == null || list.isEmpty()) {
			Logger.warn(this, "no such Email decrypt=" + decrypt);
			return null;
		}
		return list.get(0);

	}

}

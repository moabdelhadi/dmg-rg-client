package com.dmg.simplepayment.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.apache.commons.lang.StringUtils;

import com.dmg.core.persistence.FacadeFactory;
import com.dmg.simplepayment.beans.Constants;
import com.dmg.simplepayment.beans.UserAccount;
import com.dmg.simplepayment.beans.UserStatus;
import com.dmg.util.EncryptionUtil;
import com.dmg.util.FilterUtil;
import com.dmg.util.Logger;
import com.dmg.util.PropertiesManager;
import com.dmg.util.dao.DAO;
import com.dmg.util.dao.DaoException;
import com.dmg.util.dao.filtes.Filter;
import com.dmg.util.dao.filtes.FilterType;

public class UserManager {

	private final String CONFIRMATION_BASE_PATH = PropertiesManager.getInstance().getProperty("confirmation_base_path");
	private static final UserManager INSTANCE = new UserManager();

	private UserManager() {
	}

	public static UserManager getInstance() {
		return INSTANCE;
	}

	public void login(UserAccount user) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Constants.USER_ACCOUNT_EMAIL, user.getEmail());
		
		List<UserAccount> list = FacadeFactory.getFacade().list(UserAccount.class, parameters);
		
		if(list==null){
			Logger.warn(this, "Email is incorrect");
			return;
		}
		
		if(list.size() > 1){
			Logger.warn(this, "Email is dublicated Please Check");
			
		}
		
		UserAccount userAccount = list.get(0);
		String password = userAccount.getPassword();
		if(StringUtils.isEmpty(user.getPassword()) && user.getPassword().equals(password)){
			Logger.info(this, "Login Success");
		}
		
	}

	public void createUser(UserAccount user) {

		List<Filter> filterList = FilterUtil.createFilterList("email", FilterType.EQUAL, user.getEmail());
		DAO<UserAccount> dao = new DAO<UserAccount>();
		String activationLink = user.getEmail() + "==" + System.currentTimeMillis() + "==" + (Math.random() * 10000);
		String encrypt = EncryptionUtil.encrypt(activationLink);
		try {
			String decrypt = EncryptionUtil.decrypt(encrypt);
			if(decrypt!=null && decrypt.equals(activationLink)){
				System.out.println("Encription Success");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		user.setActivationString(encrypt);
		try {
			List<UserAccount> list = dao.get(UserAccount.class, filterList);
			if (list != null && list.size() > 0) {
				// TODO ERROR user Alreadi exsist;
				System.out.println("ERROR user Alreadi exsist");
				return;
			}

			dao.save(user);
			String confirmationURL = CONFIRMATION_BASE_PATH + "opt=conf&code=" + encrypt;
			System.out.println("confirmationURL= " +confirmationURL);
			// MailManager.getInstance().sendMail(user.getEmail(),
			// "Welcome to Royal Gaz",
			// "Dear Client <br/><br/>Please click in thi link bellow to Activate your account.<br/><br/>"+confirmationURL);
		} catch (DaoException e) {
			e.printStackTrace();
		}

	}

	public boolean activate(String activationString) {

		Logger.debug(this, "code="+activationString);
		try {
			DAO<UserAccount> dao = new DAO<UserAccount>();
			UserAccount user = getUserFromActivationLink(activationString, dao);

			if (user != null && activationString.equals(user.getActivationString())) {
				user.setStatus(UserStatus.ACTIVE.value());

				dao.save(user);

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
			DAO<UserAccount> dao = new DAO<UserAccount>();
			UserAccount user = getUserFromActivationLink(activationString, dao);

			if (user != null && activationString.equals(user.getActivationString())) {
				Logger.info(this, "reset enable");
				user.setPassword(password);
				user.setActivationString("");
				return true;
			}

		} catch (Exception e) {
			Logger.error(this, "error in Activate User", e);
			e.printStackTrace();
		}
		return false;
		
	}

	private UserAccount getUserFromActivationLink(String string, DAO<UserAccount> dao) throws Exception {

		String decrypt = EncryptionUtil.decrypt(string);
		String[] params = decrypt.split("==");

		if (params.length != 3) {
			Logger.warn(this, "invalid activation Link decrypt=" + decrypt);
			return null;
		}

		Logger.debug(this, "params= " + params[0] + " ' " + params[0] + " ' " + params[0]);

		List<Filter> filterList = FilterUtil.createFilterList("email", FilterType.EQUAL, params[0].trim());

		List<UserAccount> list = dao.get(UserAccount.class, filterList);

		if (list == null || list.isEmpty()) {
			Logger.warn(this, "no such Email decrypt=" + decrypt);
			return null;
		}

		return list.get(0);

	}

}

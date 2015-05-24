package com.dmg.client.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.auth.SessionHandler;
import com.dmg.core.bean.BeansFactory;
import com.dmg.core.bean.Constants;
import com.dmg.core.bean.UserAccount;
import com.dmg.core.bean.UserStatus;
import com.dmg.core.exception.DataAccessLayerException;
import com.dmg.core.persistence.FacadeFactory;
import com.dmg.util.PropertiesManager;
import com.dmg.util.SHAEncrypt;
import com.dmg.util.mail.MailManager;

//import com.dmg.util.PropertiesManager;

public class UserManager {

	private static final Logger logger = LoggerFactory.getLogger(UserManager.class);

	// private final String CONFIRMATION_BASE_PATH =
	// PropertiesManager.getInstance().getProperty("confirmation_base_path");
	private static String CONFIRM_BASE_PATH = "confirmation.base.path";
	private static final UserManager INSTANCE = new UserManager();

	private UserManager() {
	}

	public static UserManager getInstance() {
		return INSTANCE;
	}

	public UserAccount login(UserAccount user) throws UserManagerException {

		logger.debug("Start Login");
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put(Constants.USER_ACCOUNT_ID, user.getContractNo());
		parameters.put(Constants.USER_CITY, user.getCity());

		logger.debug("Start Search in user.getClass()= " + user.getClass().getName() + " ,  user.getContractNo()=" + user.getContractNo() + " , user.getCity() " + user.getCity());

		List<? extends UserAccount> list = null;

		try {

			list = FacadeFactory.getFacade().list(user.getClass(), parameters);
		} catch (DataAccessLayerException e) {
			logger.error("Error in retrieve data from database", e);
			throw new UserManagerException("Error in login, please try again later", e);
		}

		if (list == null || list.isEmpty()) {
			logger.warn("Account ID or City is incorrect");
			throw new UserManagerException("Error in login Please check you Account No, Region, and Password");
		}

		if (list.size() > 1) {
			logger.warn("Account ID or City dublicated Please Check, account=" + user.getContractNo() + " , city=" + user.getCity() + "list.size() =" +list.size());
		}

		for (UserAccount userAccount : list) {

			
//			try {
//				userAccount = (UserAccount)FacadeFactory.getFacade().refresh(userAccount);
//			} catch (DataAccessLayerException e) {
//				logger.error("Error in refreshUser data from database", e);
//				throw new UserManagerException("Error in login, please try again later", e);
//			}
			
			
			String password = userAccount.getPassword();
			logger.debug("userAccount = "+userAccount.getContractNo());
			int status = userAccount.getStatus();

			if (status == UserStatus.NEW.value()) {
				logger.warn("This user is not registered yet, please register first");
				throw new UserManagerException("This account is not registered yet, please register first");
			}

			if (StringUtils.isNotEmpty(user.getPassword()) && user.getPassword().equals(password)) {

				logger.info("password match");

				if (status == UserStatus.RESGISTERED.value()) {
					logger.warn("This user is registered, but not confirmed yet, email will be sent to your email address to activate you account");
					try {
						sendActivationEmail(userAccount);
					} catch (DataAccessLayerException e) {
						logger.error("Error in sending Activation email", e);
					}
					throw new UserManagerException("This account is registered, but not confirmed yet, email will be sent to your email address to activate you account");
				}

				if (status != UserStatus.ACTIVE.value()) {
					logger.warn("Login Success, but user is not active");
					throw new UserManagerException("Account is not active");
				}

				if (userAccount.isEnable() == null || !userAccount.isEnable()) {
					logger.warn("This account is disabled by the admin, please contact thcompany to check");
					throw new UserManagerException("This account is disabled by the admin, please contact the company to check");
				}

				SessionHandler.setUser(userAccount);
				return userAccount;

			}
		}

		throw new UserManagerException("Error in login Please check you password");

	}

	public UserAccount getAccountFromAccountID(UserAccount user) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Constants.USER_ACCOUNT_ID, user.getContractNo());
		parameters.put(Constants.USER_CITY, user.getCity());
		List<? extends UserAccount> list;
		try {

			list = FacadeFactory.getFacade().list(user.getClass(), parameters);
		} catch (DataAccessLayerException e) {
			logger.error("Account is incorrect", e);
			return null;
		}

		if (list == null || list.size() == 0) {
			logger.warn("account is incorrect");
			return null;
		}

		if (list.size() > 1) {
			logger.warn("account is dublicated Please Check");
		}

		UserAccount userAccount = list.get(0);

		try {
			userAccount = (UserAccount)FacadeFactory.getFacade().refresh(userAccount);
		} catch (DataAccessLayerException e) {
			logger.error("Error in Refresh Account", e);
			return null;
		}

		return userAccount;

	}

	// public void registerUser(UserAccount user) {
	//
	// Map<String, Object> parameters = new HashMap<String, Object>();
	// parameters.put(Constants.USER_ACCOUNT_ID, user.getContractNo());
	// parameters.put(Constants.USER_CITY, user.getCity());
	// List<UserAccount> list;
	// try {
	// list = FacadeFactory.getFacade().list(UserAccount.class, parameters);
	// } catch (DataAccessLayerException e) {
	// logger.error("Account is incorrect", e);
	// return;
	//
	// }
	//
	// if (list == null) {
	// logger.warn("Account is incorrect");
	// return;
	// }
	//
	// if (list.size() > 1) {
	// logger.warn("Account is dublicated Please Check");
	// return;
	// }
	//
	// UserAccount userAccount = list.get(0);
	//
	// // String activationLink = user.getEmail() + "==" +
	// System.currentTimeMillis() + "==" + (Math.random() * 10000);
	// // String encrypt = EncryptionUtil.encrypt(activationLink);
	// // try {
	// // String decrypt = EncryptionUtil.decrypt(encrypt);
	// // if (decrypt != null && decrypt.equals(activationLink)) {
	// // System.out.println("Encription Success");
	// // }
	// // } catch (Exception e1) {
	// // // TODO Auto-generated catch block
	// // e1.printStackTrace();
	// // }
	// // user.setActivationString(encrypt);
	//
	// userAccount.setEmail(user.getEmail());
	// userAccount.setPobox(user.getPobox());
	// userAccount.setPoboxCity(user.getPoboxCity());
	// userAccount.setName(user.getName());
	// userAccount.setPassword(user.getPassword());
	// userAccount.setPhone(user.getPhone());
	// userAccount.setStatus(UserStatus.NEW.value());
	//
	// try {
	// FacadeFactory.getFacade().store(userAccount);
	// } catch (DataAccessLayerException e) {
	// logger.error("Error in saving user acount=" +
	// userAccount.getContractNo(), e);
	// return;
	// }
	//
	// }

	public UserAccount validateAccount(Map<String, Object> parameters) throws DataAccessLayerException {

		List<? extends UserAccount> list;
		try {

			Object city = parameters.get(Constants.USER_CITY);
			UserAccount userAccount = BeansFactory.getInstance().getUserAccount(city.toString());

			list = FacadeFactory.getFacade().list(userAccount.getClass(), parameters);
		} catch (DataAccessLayerException e) {
			logger.error("Account is incorrect", e);
			throw new DataAccessLayerException("System Error occurred please call: 800-RGAS");

		}

		if (list == null || list.isEmpty()) {
			logger.warn("Account is incorrect");
			throw new DataAccessLayerException("Account does not exist");
		}

		if (list.size() > 1) {
			logger.warn("Account is dublicated Please Check");
			throw new DataAccessLayerException("System Error occurred please call: 800-RGAS");
		}

		UserAccount userAccountRes = list.get(0);
		try {
			userAccountRes= (UserAccount) FacadeFactory.getFacade().refresh(userAccountRes);
		} catch (DataAccessLayerException e) {
			logger.error("Error in Refresh Account", e);
			return null;
		}
		
		return userAccountRes;
	}

	public void updateAccount(UserAccount userAccount) throws DataAccessLayerException {
		try {
			FacadeFactory.getFacade().store(userAccount);
		} catch (DataAccessLayerException e) {
			logger.error("Error occured while updating record is incorrect", e);
			throw new DataAccessLayerException("System Error occurred please call: 800-RGAS");
		}
	}

	// public boolean activate(String activationString) {
	//
	// logger.debug("code=" + activationString);
	// try {
	// UserAccount user = getUserFromActivationLink(activationString);
	//
	// if (user != null && activationString.equals(user.getActivationString()))
	// {
	// user.setStatus(UserStatus.RESGISTERED.value());
	//
	// FacadeFactory.getFacade().store(user);
	//
	// logger.info("activation Success");
	// return true;
	// }
	//
	// } catch (Exception e) {
	// logger.error("error in Activate User", e);
	// e.printStackTrace();
	// }
	// return false;
	//
	// }

	// public boolean resetPassword(String activationString, String password) {
	//
	// try {
	// UserAccount user = getUserFromActivationLink(activationString);
	//
	// if (user != null && activationString.equals(user.getActivationString()))
	// {
	// logger.info("reset enable");
	// user.setPassword(password);
	// user.setActivationString("");
	// FacadeFactory.getFacade().store(user);
	// return true;
	// }
	//
	// } catch (Exception e) {
	// logger.error("error in Activate User", e);
	// }
	// return false;
	//
	// }

	public void sendActivationEmail(UserAccount user) throws DataAccessLayerException {

		String hashKey = user.getActivationKey();

		if (hashKey == null || hashKey.trim().isEmpty()) {
			hashKey = SHAEncrypt.encryptKey(user.getCity() + "_" + user.getContractNo() + "_" + System.currentTimeMillis());
			user.setActivationKey(hashKey);
			FacadeFactory.getFacade().store(user);
		}

		String msgBody = "<div style=\"text-align:left\"><p>Hello " + user.getName()
				+ ",</p><p>Thanks for registering with Royal Development for Gas Works online Service</p><p>Please follow the link below to Activate your account.</p><p>"
				+ PropertiesManager.getInstance().getProperty(CONFIRM_BASE_PATH) + "#!activationPage/" + user.getActivationKey() + "/" + user.getCity() + "/" + user.getContractNo()
				+ "</p><p>Regards,</p><p>Royal Development for Gas Works</p></div>";
		MailManager.getInstance().sendMail(user.getEmail(), "Account Activation", msgBody);
		// "Please click here: " +
		// PropertiesManager.getInstance().getProperty(CONFIRM_BASE_PATH) +
		// "#!activationPage/" + user.getActivationKey() + "/" + user.getCity()
		// + "/" + user.getContractNo()
		// + " to activate your account");
	}

	// private UserAccount getUserFromActivationLink(String string) throws
	// Exception {
	//
	// String decrypt = EncryptionUtil.decrypt(string);
	// String[] params = decrypt.split("==");
	//
	// if (params.length != 3) {
	// logger.warn("invalid activation Link decrypt=" + decrypt);
	// return null;
	// }
	//
	// logger.debug("params= " + params[0] + " ' " + params[0] + " ' " +
	// params[0]);
	//
	// Map<String, Object> parameters = new HashMap<String, Object>();
	// parameters.put(Constants.USER_ACCOUNT_EMAIL, params[0].trim());
	//
	// List<UserAccount> list =
	// FacadeFactory.getFacade().list(UserAccount.class, parameters);
	//
	// if (list == null || list.isEmpty()) {
	// logger.warn("no such Email decrypt=" + decrypt);
	// return null;
	// }
	// return list.get(0);
	//
	// }

}

//package com.dmg.client.servlet;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TimeZone;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import okhttp3.FormBody;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.dmg.client.auth.SessionHandler;
//import com.dmg.client.auth.util.PasswordUtil;
//import com.dmg.client.payment.PaymentManager;
//import com.dmg.core.bean.BeansFactory;
//import com.dmg.core.bean.NewUserRegistration;
//import com.dmg.core.bean.NewUserRegistrationDu;
//import com.dmg.core.bean.Transaction;
//import com.dmg.core.bean.UserStatus;
//import com.dmg.core.exception.DataAccessLayerException;
//import com.dmg.core.persistence.FacadeFactory;
//import com.dmg.util.PropertiesManager;
//import com.google.gson.Gson;
//
///**
// * Servlet implementation class NewUserApplication
// */
//@WebServlet("/regUserProcessPay")
//public class NewUserProcessPayBack extends HttpServlet {
//
//	private final String CITY_FIELD = "city";
//	private final String USERNAME_FIELD = "username";
//	private final String EMAIL_FIELD = "email";
//	private final String PASSWORD_FIELD = "password";
//	private final String PASSWORD_CONFIRM_FIELD = "password_confirm";
//	private final String BUILDING_NO_FIELD = "buildingNumber";
//	private final String APART_NO_FIELD = "appartmentNumber";
//	private final String METETR_NO_FIELD = "meterNo";
//	private final String METER_READING_FIELD = "meterReading";
//	private final String START_DATE_FIELD = "startDate";
//	private final String POBOX_FIELD = "pobox";
//	private final String POBOX_CITY_FIELD = "poboxCity";
//	private final String PHONE_FIELD = "phone";
//	private final String MOBILE_FIELD = "mobile";
//	private final String EMIRATES_ID_FIELD = "emiratesId";
//	private final String CAPTCH_FIELD = "g-recaptcha-response";
//
//	private final String captchaSecret = PropertiesManager.getInstance().getProperty("recaptcha.secret");
//	private final String captchaReq = PropertiesManager.getInstance().getProperty("recaptcha.url");
//
//	private final String requireList[] = { CITY_FIELD, USERNAME_FIELD, EMAIL_FIELD, PASSWORD_FIELD, BUILDING_NO_FIELD, APART_NO_FIELD, METER_READING_FIELD, METETR_NO_FIELD, START_DATE_FIELD,
//			MOBILE_FIELD, EMIRATES_ID_FIELD };
//
//	private static volatile long lastRef = 0;
//
//	private static final long serialVersionUID = 1L;
//	private static final Logger log = LoggerFactory.getLogger(NewUserProcessPayBack.class);
//
//	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//	OkHttpClient client = new OkHttpClient();
//	private final Gson gson = new Gson();
//
//	/**
//	 * @see HttpServlet#HttpServlet()
//	 */
//	public NewUserProcessPayBack() {
//		super();
//	}
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doProcess(request, response);
//	}
//
//	private void doProcess(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			log.debug("Accept get");
//			HttpSession session = request.getSession();
//			NewUserRegistration user = (NewUserRegistration) session.getAttribute("userVal");
//			
//			List<String> errors = new ArrayList<String>();
//			if (user == null) {
//				errors.add("Error process new user, no user in session");
//				responseBadInput(request, response, errors);
//				return;
//			}
//
//			List<String> validate = validate(user);
//			if (validate != null && validate.size() > 0) {
//				responseBadInput(request, response, validate);
//				return;
//			}
//			
//			PaymentManager manager = PaymentManager.getInstance();
//			Map<String, String> postFields = manager.getNewUserPostFields(user, 10);
//			user.setAmount(postFields.get("vpc_Amount"));
////			user.setApproveStatus("SENT");
//			user.setMerchant(postFields.get("vpc_Merchant"));
//			user.setMerchTxnRef("vpc_MerchTxnRef");
//			user.setOrderInfo("vpc_OrderInfo");
//			user.setStatus(UserStatus.INBANK.value());
//			
//			FacadeFactory.getFacade().store(user);
//			
//			//Resirect Request;
//			
//			//Transaction txn = manager.getPaymentByTxnRef(postFields.get("vpc_MerchTxnRef"));
//			
//			// responseSuccess(request, response, user);
//
//		} catch (Exception e) {
//			log.error("error in saving data");
//			List<String> validate = new ArrayList<String>();
//			validate.add("Un Known Error Accurs during your request . Please Try Again Later");
//			responseBadInput(request, response, validate);
//		}
//	}
//
//	private void responseSuccess(HttpServletRequest request, HttpServletResponse response, NewUserRegistration user) {
//		try {
//
//			HttpSession session = request.getSession();
//			session.setAttribute("userVal", user);
//
//			request.setAttribute("user", user);
//			request.setAttribute("requestStatus", "success");
//			RequestDispatcher view = request.getRequestDispatcher("/views/newapplication/UserDetails.jsp");
//
//			// response.setHeader("Content-Type","text/html");
//			// response.setHeader("Expires","Mon, 26 Jul 1997 05:00:00 GMT");
//			// response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
//			// response.setHeader("Pragma","no-cache");
//
//			view.forward(request, response);
//		} catch (Exception e) {
//			log.error("error in return response", e);
//		}
//
//	}
//
//	private void responseBadInput(HttpServletRequest request, HttpServletResponse response, List<String> validate) {
//
//		try {
//			request.setAttribute("errors", validate);
//			request.setAttribute("requestStatus", "error");
//
//			RequestDispatcher view = request.getRequestDispatcher("/views/newapplication/newClientApplication.jsp");
//
//			// response.setHeader("Content-Type","text/html");
//			// response.setHeader("Expires","Mon, 26 Jul 1997 05:00:00 GMT");
//			// response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
//			// response.setHeader("Pragma","no-cache");
//
//			view.forward(request, response);
//		} catch (Exception e) {
//			log.error("error in return response", e);
//		}
//
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doProcess(request, response);
//	}
//
//	private List<String> validate(NewUserRegistration user) {
//
//		List<String> list = new ArrayList<String>();
//		if (user.getCity() == null) {
//			list.add("City is null");
//			return list;
//		}
//
//		NewUserRegistration newUser = BeansFactory.getInstance().getNewUser(user.getCity());
//
//		try {
//
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("refNo", user.getRefNo());
//			List<? extends NewUserRegistration> listUsers = FacadeFactory.getFacade().list(newUser.getClass(), map);
//
//			if (listUsers == null || listUsers.size() != 1) {
//				log.error("User Reference Incorrect");
//				list.add("User Reference Incorrect");
//				return list;
//			}
//		} catch (DataAccessLayerException e) {
//			log.error("Error in get referenced User");
//			list.add("Error in get referenced User");
//			return list;
//		}
//
//		return list;
//	}
//
//	private boolean passwordCheck(HttpServletRequest request) {
//		String pass01 = request.getParameter(PASSWORD_FIELD);
//		String pass02 = request.getParameter(PASSWORD_CONFIRM_FIELD);
//		if (pass01 != null && pass01.equals(pass02)) {
//			return true;
//		}
//		return false;
//	}
//
//	private boolean isRobot(HttpServletRequest request) {
//
//		String captch = request.getParameter(CAPTCH_FIELD);
//		if (captch == null || captch.isEmpty()) {
//			return true;
//		}
//
//		RequestBody body = new FormBody.Builder().add("secret", captchaSecret).add("response", captch).build();
//
//		Request clientRequest = new Request.Builder().url(captchaReq).post(body).build();
//
//		try {
//			Response response = client.newCall(clientRequest).execute();
//
//			if (!response.isSuccessful()) {
//				log.error("error reading from captch Server" + response);
//			}
//
//			CaptchaRespone gist = gson.fromJson(response.body().charStream(), CaptchaRespone.class);
//
//			if (gist == null) {
//				log.warn("Cannot Read Response of Captcha gist==null");
//				return true;
//			}
//
//			if (gist.getSuccess() == null) {
//				log.warn("Cannot Read Response of Captcha gist.getSuccess==null");
//				return true;
//			}
//
//			if (gist.getSuccess() == false) {
//				log.warn("Cannot Read Response of Captcha getSuccess==false");
//				for (String string : gist.getErrorCodes()) {
//					log.warn(string);
//				}
//				return true;
//			}
//
//			return false;
//
//		} catch (Exception e) {
//			log.error("error in reading CAptch", e);
//		}
//		return true;
//
//	}
//
//	private boolean requireCheck(HttpServletRequest request, String fieldName) {
//		String parameter = request.getParameter(fieldName);
//		if (parameter == null || parameter.trim().length() == 0) {
//			return false;
//		}
//		return true;
//	}
//
//	private NewUserRegistration createUser(HttpServletRequest request) {
//
//		String city = request.getParameter(CITY_FIELD);
//		NewUserRegistration newUser = BeansFactory.getInstance().getNewUser(city);
//
//		if (newUser == null) {
//			return null;
//		}
//
//		Date now = Calendar.getInstance(TimeZone.getTimeZone("Asia/Dubai")).getTime();
//		newUser.setCreationDate(now);
//		newUser.setUpdateDate(now);
//		newUser.setEmail(request.getParameter(EMAIL_FIELD));
//		String passNormal = request.getParameter(PASSWORD_FIELD);
//		String generateHashedPassword = PasswordUtil.generateHashedPassword(passNormal);
//		newUser.setPassword(generateHashedPassword);
//		newUser.setName(request.getParameter(USERNAME_FIELD));
//		newUser.setCity(request.getParameter(CITY_FIELD));
//		newUser.setBuildingNumber(request.getParameter(BUILDING_NO_FIELD));
//		newUser.setAppartmentNumber(request.getParameter(APART_NO_FIELD));
//		newUser.setPhone(request.getParameter(PHONE_FIELD));
//		newUser.setMobile(request.getParameter(MOBILE_FIELD));
//		newUser.setPobox(request.getParameter(POBOX_FIELD));
//		newUser.setPoboxCity(request.getParameter(POBOX_CITY_FIELD));
//		newUser.setMeterReading(request.getParameter(METER_READING_FIELD));
//		newUser.setMeterNo(request.getParameter(METETR_NO_FIELD));
//		newUser.setEmiratesId(request.getParameter(EMIRATES_ID_FIELD));
//		newUser.setStatus(UserStatus.NEW.value());
//		newUser.setSyncStatus(0);
//		newUser.setRefNo(createRefNo());
//		return newUser;
//	}
//
//	private synchronized String createRefNo() {
//		long currentTimeMillis = System.currentTimeMillis();
//		long ref = (currentTimeMillis - 1467316800000L) / 1000;
//		if (ref != lastRef) {
//			lastRef = ref;
//			return String.valueOf(ref);
//		}
//
//		while (ref == lastRef) {
//			try {
//				log.debug("before wait");
//				wait(1100L);
//				log.debug("after wait");
//				currentTimeMillis = System.currentTimeMillis();
//				ref = (currentTimeMillis - 1467316800000L) / 1000;
//			} catch (InterruptedException e) {
//				log.error("error in waiting thread", e);
//			}
//
//		}
//		lastRef = ref;
//		return String.valueOf(ref);
//	}
//
//}

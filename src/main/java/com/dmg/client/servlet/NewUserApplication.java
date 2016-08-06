package com.dmg.client.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.auth.util.PasswordUtil;
import com.dmg.client.user.UserManager;
import com.dmg.core.bean.BeansFactory;
import com.dmg.core.bean.NewUserRegistration;
import com.dmg.core.bean.UserStatus;
import com.dmg.core.exception.DataAccessLayerException;
import com.dmg.core.persistence.FacadeFactory;

/**
 * Servlet implementation class NewUserApplication
 */
@WebServlet("/newUserApplication")
public class NewUserApplication extends HttpServlet {

	private final String CITY_FIELD = "city";
	private final String USERNAME_FIELD = "username";
	private final String EMAIL_FIELD = "email";
	private final String PASSWORD_FIELD = "password";
	private final String PASSWORD_CONFIRM_FIELD = "password_confirm";
	private final String BUILDING_NO_FIELD = "buildingNumber";
	private final String APART_NO_FIELD = "appartmentNumber";
	private final String METETR_NO_FIELD = "meterNo";
	private final String METER_READING_FIELD = "meterReading";
	private final String START_DATE_FIELD = "startDate";
	private final String POBOX_FIELD = "pobox";
	private final String POBOX_CITY_FIELD = "poboxCity";
	private final String PHONE_FIELD = "phone";
	private final String MOBILE_FIELD = "mobile";
	private final String EMIRATES_ID_FIELD = "emiratesId";

	private final String requireList[] = { CITY_FIELD, USERNAME_FIELD, EMAIL_FIELD, PASSWORD_FIELD, BUILDING_NO_FIELD,
			APART_NO_FIELD, METER_READING_FIELD, METETR_NO_FIELD, START_DATE_FIELD, MOBILE_FIELD, EMIRATES_ID_FIELD };

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(NewUserApplication.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewUserApplication() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response) {
		try {
			log.debug("Accept get");
			List<String> validate = validate(request);
			if (validate != null && validate.size() > 0) {
				responseBadInput(response);
				return;
			}
			NewUserRegistration user = createUser(request);
			FacadeFactory.getFacade().store(user);

		} catch (DataAccessLayerException e) {
			log.error("error in saving data");
			responseBadInput(response);
		}
	}

	private void responseBadInput(HttpServletResponse response) {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		doProcess(request, response);
	}

	private List<String> validate(HttpServletRequest request) {
		List<String> list = new ArrayList<String>();

		if (isRobot(request)) {
			list.add("Please Fill Data Using human ineraction.");
			return list;
		}

		for (String fname : requireList) {
			if (requireCheck(request, fname)) {
				list.add("fname is required");
			}
		}

		if (passwordCheck(request)) {
			list.add("Password must Match");
		}

		return list;
	}

	private boolean passwordCheck(HttpServletRequest request) {
		String pass01 = request.getParameter(PASSWORD_FIELD);
		String pass02 = request.getParameter(PASSWORD_CONFIRM_FIELD);
		if (pass01 != null && pass01.equals(pass02)) {
			return true;
		}
		return false;
	}

	private boolean isRobot(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean requireCheck(HttpServletRequest request, String fieldName) {
		String parameter = request.getParameter(fieldName);
		if (parameter == null || parameter.trim().length() == 0) {
			return false;
		}
		return true;
	}

	private NewUserRegistration createUser(HttpServletRequest request) {

		String city = request.getParameter(CITY_FIELD);
		NewUserRegistration newUser = BeansFactory.getInstance().getNewUser(city);

		if (newUser == null) {
			return null;
		}

		Date now = Calendar.getInstance(TimeZone.getTimeZone("Asia/Dubai")).getTime();
		newUser.setCreationDate(now);
		newUser.setUpdateDate(now);
		newUser.setEmail(request.getParameter(EMAIL_FIELD));
		String passNormal = request.getParameter(PASSWORD_FIELD);
		String generateHashedPassword = PasswordUtil.generateHashedPassword(passNormal);
		newUser.setPassword(generateHashedPassword);
		newUser.setName(request.getParameter(USERNAME_FIELD));
		newUser.setCity(request.getParameter(CITY_FIELD));
		newUser.setBuildingNumber(request.getParameter(BUILDING_NO_FIELD));
		newUser.setAppartmentNumber(request.getParameter(APART_NO_FIELD));
		newUser.setPhone(request.getParameter(PHONE_FIELD));
		newUser.setMobile(request.getParameter(MOBILE_FIELD));
		newUser.setPobox(request.getParameter(POBOX_FIELD));
		newUser.setPoboxCity(request.getParameter(POBOX_CITY_FIELD));
		newUser.setMeterReading(request.getParameter(METER_READING_FIELD));
		newUser.setMeterNo(request.getParameter(METETR_NO_FIELD));
		newUser.setEmiratesId(request.getParameter(EMIRATES_ID_FIELD));
		newUser.setStatus(UserStatus.NEW.value());
		newUser.setSyncStatus(0);

		return newUser;
	}

}

package com.dmg.client.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.payment.PaymentManager;

@WebServlet("/payResponse")
public class PayServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory
			.getLogger(PaymentManager.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {

		log.debug("get");
		Map fields = new HashMap<String, String>();
		int count = 0;
		Enumeration<String> parameterNames = request.getParameterNames();
		
		for (Enumeration e = parameterNames; e.hasMoreElements();) {
			String fieldName = (String) e.nextElement();
			String fieldValue = request.getParameter(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				fields.put(fieldName, fieldValue);
				log.debug("fiels" + count++ + ": " + fieldName + " = "
						+ fieldValue);
			}
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {

		log.debug("post");
		Map fields = new HashMap<String, String>();
		int count = 0;
		Enumeration<String> parameterNames = request.getParameterNames();
		for (Enumeration e = parameterNames; e.hasMoreElements();) {
			String fieldName = (String) e.nextElement();
			String fieldValue = request.getParameter(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				fields.put(fieldName, fieldValue);
				log.debug("fiels" + count++ + ": " + fieldName + " = "
						+ fieldValue);
			}
		}
	}

	private String printParams(Map<String, String[]> parameterMap) {

		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (String key : parameterMap.keySet()) {

			System.out.println(key);

			if (key.startsWith("v-") || key.equals("theme")) {
				continue;
			}

			if (!first) {
				result.append(",");
			}

			String[] strings = parameterMap.get(key);
			result.append(key);
			result.append("=");

			if (strings != null) {
				String value = strings[0];
				result.append(value);
			}
			first = false;
		}

		return result.toString();

	}

}

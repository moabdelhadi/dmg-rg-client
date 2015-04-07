package com.dmg.client.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author Mohammad Abdelhadi
 *
 */

@WebServlet("/loginServlet")
public class LoginCheck  extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(LoginCheck.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		
		Enumeration<String> parameterNames = req.getParameterNames();
		while(parameterNames.hasMoreElements()){
			String nextElement = parameterNames.nextElement();
			log.debug("nextElement"+nextElement);
		}
	}

}

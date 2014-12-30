package com.dmg.client.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class PayServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Map<String, String[]> parameterMap = req.getParameterMap();
		System.out.println("get");
		printParams(parameterMap);
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("post");
		Object attribute = req.getAttribute("paras");
		System.out.println(attribute);
		Enumeration<String> attributeNames = req.getAttributeNames();
		 while (attributeNames.hasMoreElements()) {
			String string = (String) attributeNames.nextElement();
			System.out.println(string);
			
		}
//		Map<String, String[]> parameterMap =
//		printParams(parameterMap);
//		super.doPost(req, resp);
	}
	
	
private String printParams(Map<String, String[]> parameterMap) {
		
		
		StringBuilder result= new StringBuilder();
		boolean first=true;
		for (String key : parameterMap.keySet()) {
			
			System.out.println(key);
			
			if(key.startsWith("v-") || key.equals("theme")){
				continue;
			}
			
			if(!first){
				result.append(",");
			}
			

			
			String[] strings = parameterMap.get(key);
			result.append(key);
			result.append("=");
			
			if(strings!=null){
				String value=strings[0];
				result.append(value);
			}
			first=false;
		}
		
		return result.toString();
		
	}

	
	
}

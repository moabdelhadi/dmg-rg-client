//package com.dmg.simplepayment;
//
//import javax.servlet.annotation.WebServlet;
//
//import com.dmg.simplepayment.views.UserPage;
//import com.vaadin.annotations.Theme;
//import com.vaadin.annotations.VaadinServletConfiguration;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.server.VaadinServlet;
//import com.vaadin.ui.UI;
//
//@SuppressWarnings("serial")
//@Theme("simplepayment")
//public class SimplepaymentUI extends UI {
//
//	@WebServlet(value = {"/userAccount/*", "/VAADIN/*"} , asyncSupported = true)
//	@VaadinServletConfiguration(productionMode = false, ui = SimplepaymentUI.class)
//	public static class Servlet extends VaadinServlet {
//
//	}
//	
//	@Override
//	protected void init(VaadinRequest request) {
//		UserPage userpage = new UserPage();
//		setContent(userpage.getMainlayout());
//		
//	}
//	
//}
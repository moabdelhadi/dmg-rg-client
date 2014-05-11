package com.dmg.simplepayment.views;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("dmg-theme")
public class NavigatorUI extends UI {
	private static final long serialVersionUID = 511085335415683713L;

	Navigator navigator;


	@WebServlet(value = { "/client/*", "/VAADIN/*" }, asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = NavigatorUI.class)
	public static class Servlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}


	@Override
	protected void init(VaadinRequest request) {
		
		getPage().setTitle("Royal Gas");

		// Create a navigator to control the views
		navigator = new Navigator(this, this);

		// Create and register the views
		navigator.addView(Views.LOGIN, new Login(navigator));
		navigator.addView(Views.USER_PAGE, new UserPage(navigator));
		
		navigator.addViewChangeListener(new ViewChangeListener() {
			
			private static final long serialVersionUID = 9119189310920723601L;

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public void afterViewChange(ViewChangeEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}

// END-EXAMPLE: advanced.navigator.basic
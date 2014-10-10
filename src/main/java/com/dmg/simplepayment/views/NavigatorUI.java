package com.dmg.simplepayment.views;

import javax.servlet.annotation.WebServlet;

import org.apache.commons.lang.StringUtils;

import com.dmg.client.auth.SessionHandler;
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
	protected void init(final VaadinRequest request) {
		
		getPage().setTitle("Royal Gas");

		// Create a navigator to control the views
		navigator = new Navigator(this, this);

		// Create and register the views
		navigator.addView(Views.LOGIN, new Login(navigator));
		navigator.addView(Views.USER_PAGE, new AccountOverview(navigator));
		navigator.addView(Views.EDIT_PROFILE_PAGE, new EditUserProfile(navigator));
		navigator.addView(Views.ACTIVATION_PAGE, new WelcomeAfterRegister(navigator));
		navigator.addView(Views.CHANGE_PASSWORD, new ChangePassword(navigator));
		
		navigator.addViewChangeListener(new ViewChangeListener() {
			
			private static final long serialVersionUID = 9119189310920723601L;

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				
				String viewName = event.getViewName();
				
				if(StringUtils.equals(viewName, Views.LOGIN) || StringUtils.equals(viewName,  Views.EDIT_PROFILE_PAGE)){
					return true;
				}
				
				if (SessionHandler.get() == null) {
					String fragmentAndParameters = viewName;
					if (event.getParameters() != null) {
						fragmentAndParameters += "/";
						fragmentAndParameters += event.getParameters();
					}
					navigator.navigateTo(Views.LOGIN);
					return false;

				} else {
					return true;

				}
			}
			
			@Override
			public void afterViewChange(ViewChangeEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}

// END-EXAMPLE: advanced.navigator.basic
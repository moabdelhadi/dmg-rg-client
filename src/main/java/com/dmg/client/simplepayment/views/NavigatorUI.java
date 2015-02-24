package com.dmg.client.simplepayment.views;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.auth.SessionHandler;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("dmg-theme")
public class NavigatorUI extends UI {

	private static final long serialVersionUID = 511085335415683713L;
	private static final Logger logger = LoggerFactory.getLogger(NavigatorUI.class);

	Navigator navigator;

	public static class Servlet extends VaadinServlet {
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
		navigator.addView(Views.RIGISTER_PROFILE_PAGE, new RegisterUserProfile(navigator));
		navigator.addView(Views.ACTIVATION_PAGE, new ActivationView(navigator));
		navigator.addView(Views.CHANGE_PASSWORD, new ChangePassword(navigator));
		navigator.addView(Views.FORGOT_PASSWORD, new ForgotPassword(navigator));
		navigator.addViewChangeListener(new ViewChangeListener() {

			private static final long serialVersionUID = 9119189310920723601L;
			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {

				logger.info("get in  beforeViewChange()");

				String viewName = event.getViewName();
				logger.info("redirect to non authorized Page viewName");

				if (StringUtils.equals(viewName, Views.LOGIN)  || StringUtils.equals(viewName, Views.RIGISTER_PROFILE_PAGE)
						|| event.getViewName().equals(Views.ACTIVATION_PAGE)) {
					logger.info("redirect to non authorized Page");
					return true;
				}

				if (SessionHandler.get() == null) {
					logger.info("non authorized user");
					if (event.getViewName().equals(Views.FORGOT_PASSWORD)) {
						return true;
					}
					// String fragmentAndParameters = viewName;
					// if (event.getParameters() != null) {
					// fragmentAndParameters += "/";
					// fragmentAndParameters += event.getParameters();
					// }
					navigator.navigateTo(Views.LOGIN);
					return false;

				} else {
					return true;

				}
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				logger.info("get in  afterViewChange()");

			}
		});
	}
}

// END-EXAMPLE: advanced.navigator.basic
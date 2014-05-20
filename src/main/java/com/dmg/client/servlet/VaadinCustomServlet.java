package com.dmg.client.servlet;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.auth.SessionHandler;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;

public class VaadinCustomServlet extends VaadinServlet implements SessionInitListener, SessionDestroyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6931082245174546300L;
	private static final Logger logger = LoggerFactory.getLogger(VaadinCustomServlet.class);

	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();
		getService().addSessionDestroyListener(this);
		getService().addSessionInitListener(this);

	}

	@Override
	public void sessionDestroy(SessionDestroyEvent event) {
		logger.warn("SessionDestroyEvent is called now!!!");
	}

	@Override
	public void sessionInit(SessionInitEvent event) throws ServiceException {
		SessionHandler.initialize();
		logger.warn("Session is initialized");
	}

}

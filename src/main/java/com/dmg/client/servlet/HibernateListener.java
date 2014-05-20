package com.dmg.client.servlet;

import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.core.persistence.FacadeFactory;
import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.PooledDataSource;

public class HibernateListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(HibernateListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.warn("SERVER IS GOING DOWN -> shutting down hibernate");
		FacadeFactory.clear();
		@SuppressWarnings("unchecked")
		Set<PooledDataSource> pooledDataSourceSet = C3P0Registry.getPooledDataSources();
		for (PooledDataSource pooledDataSource : pooledDataSourceSet) {
			try {
				logger.warn("!!!CLOSING!!! the pooledDataSource");
				pooledDataSource.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// http://www.network-science.de/ascii/ :: rectangles
		logger.warn("Starting DMG-RG-ADMIN application DB session creation");
		logger.warn("        db         88888888ba,    88b           d88  88  888b      88");
		logger.warn("       d88b        88      `'8b   888b         d888  88  8888b     88");
		logger.warn("      d8'`8b       88        `8b  88`8b       d8'88  88  88 `8b    88");
		logger.warn("	  d8'  `8b      88         88  88 `8b     d8' 88  88  88  `8b   88");
		logger.warn("	 d8YaaaaY8b     88         88  88  `8b   d8'  88  88  88   `8b  88");
		logger.warn("   d8''''''''8b    88         8P  88   `8b d8'   88  88  88    `8b 88");
		logger.warn("  d8'        `8b   88      .a8P   88    `888'    88  88  88     `8888");
		logger.warn(" d8'          `8b  88888888Y''    88     `8'     88  88  88      `888");
		try {
			FacadeFactory.registerFacade("dmg-rg-client", true);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

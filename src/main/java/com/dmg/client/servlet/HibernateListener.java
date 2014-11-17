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
		Set<PooledDataSource> pooledDataSourceSet = C3P0Registry
				.getPooledDataSources();
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

		logger.warn("Starting DMG-RG-ADMIN application DB session creation");
		logger.warn("   ___     _       ___     ___    _  _    _____");
		logger.warn("   / __|   | |     |_ _|   | __|  | \\| |  |_   _|");
		logger.warn("| (__    | |__    | |    | _|   | .` |    | |");
		logger.warn("\\___|   |____|  |___|   |___|  |_|\\_|   _|_|_");
		logger.warn("_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|");
		logger.warn("\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'");

		try {
			FacadeFactory.registerFacade("dmg-rg-client", true);
		} catch (InstantiationException e) {
			logger.error("error in inialize the contextInitialized", e);
		} catch (IllegalAccessException e) {
			logger.error("error in inialize the contextInitialized", e);
		}

	}
}

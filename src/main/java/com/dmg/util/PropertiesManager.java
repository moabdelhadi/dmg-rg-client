package com.dmg.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.servlet.HibernateListener;

public class PropertiesManager {

	private static PropertiesManager manager = null;
	private static Properties properties = null;
	private static final Logger log = LoggerFactory.getLogger(PropertiesManager.class);
	

	private static final String PROPERTY_FILE = "conf.properties";
	public static final String  DATABASE_CONNECTION = "db_connection";


	private PropertiesManager() {
		loadProperties(PROPERTY_FILE);

	}

	public static PropertiesManager getInstance() {
		if (manager != null) {
			return manager;
		}
		synchronized (PropertiesManager.class) {
			if (PropertiesManager.manager == null) {
				PropertiesManager.manager = new PropertiesManager();
			}
		}
		return manager;
	}

	private void loadProperties(String fileName) {
		try {
			properties = new Properties();

			InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("../../WEB-INF/config/config.properties");
			InputStreamReader reader = new InputStreamReader(resourceAsStream, "UTF8");

			properties.load(reader);

		} catch (FileNotFoundException e) {
			log.error( "Error in loading property file", e);
			e.printStackTrace();

		} catch (IOException e) {
			log.error( "Error in loading property file", e);
			e.printStackTrace();
		}

	}

	public String getProperty(String fileName) {

		Object object = properties.get(fileName);
		return object.toString();

	}

	public int getPropertyInt(String fileName) {

		Object object = properties.get(fileName);
		
		int parseInt = Integer.parseInt(object.toString());
		
		
		return parseInt;

	}

}

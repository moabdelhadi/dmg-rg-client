//package com.dmg.util;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.Properties;
//
//import org.apache.log4j.PropertyConfigurator;
//
//public class Logger {
//
//	private static org.apache.log4j.Logger logger ;
//
//
//	static {
//		
//		logger = org.apache.log4j.Logger.getLogger("Phoenecia");
//		Properties properties = new Properties();
//		try {
////			properties.load(new FileInputStream(new File("/var/lib/tomcat7/webapps/RoyalGas/WEB-INF/log/log4j.properties")));
////			properties.load(new FileInputStream(new File("D:/Developing-tools/workspace/RoyalGas/WebContent/WEB-INF/log/log4j.properties")));
//			properties.load(new FileInputStream(new File("F:/workSpaceVaadin/RoyalGas/WebContent/WEB-INF/log/log4j.properties")));
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		PropertyConfigurator.configure(properties);
//	}
//	
//	
//	
//	public static void debug(Object object, String message) {
//		logger.debug(getLogMessage(object.getClass(), message));
//	}
//
//	public static void info(Object object, String message) {
//		logger.info(getLogMessage(object.getClass(), message));
//	}
//
//	public static void warn(Object object, String message) {
//		logger.warn(getLogMessage(object.getClass(), message));
//	}
//
//	public static void error(Object object, String message) {
//		logger.error(getLogMessage(object.getClass(), message));
//	}
//
//	public static void error(Object object, String message, Throwable exception) {
//		logger.error(getLogMessage(object.getClass(), message), exception);
//	}
//
//	public static void fatal(Object object, String message) {
//		logger.fatal(getLogMessage(object.getClass(), message));
//	}
//
//	public static void fatal(Object object, String message, Throwable exception) {
//		logger.fatal(getLogMessage(object.getClass(), message), exception);
//	}
//	
//	private static String getLogMessage(Class<?extends Object> clazz, String message){
//		return clazz.getName()+": " +message;
//	}
//}

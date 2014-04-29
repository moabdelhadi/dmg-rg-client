package com.dmg.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtilsBean;




public class BeanWraperUtil {

	private static PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();

	private BeanWraperUtil() {

	}

	public static Object getAttribute(Object object, String attributeName) {
		try {
			if (!attributeName.contains(".")) {
				return propertyUtilsBean.getProperty(object, attributeName);
			}

			String[] attStrings = attributeName.split("\\.");
			Object value = object;
			for (String each : attStrings) {
				if (each == null || each.trim().equals("")) {
					continue;
				}
				value = propertyUtilsBean.getProperty(value, each);
			}
			return value;

		} catch (Exception e) {
			handleBeanException(e);
		}
		return null;
	}

	public static void setAttribute(Object object, String attributeName, Object value) {
		try {
			propertyUtilsBean.setProperty(object, attributeName, value);
		} catch (Exception e) {
			handleBeanException(e);
		}
	}

	public static void setIndexedAttribute(Object object, String attributeName, Object value) {
		try {
			propertyUtilsBean.setIndexedProperty(object, attributeName, value);
		} catch (Exception e) {
			handleBeanException(e);
		}
	}

	public static String getFieldNameFromGetMethod(String methodName) {
		String value = methodName.substring(methodName.indexOf("get") + 3);
		char[] charArray = value.toCharArray();
		StringBuffer buffer = new StringBuffer();
		int i = 0;
		for (char c : charArray) {
			if (i == 0) {
				buffer.append(Character.toLowerCase(c));
			} else {
				buffer.append(c);
			}
			i++;
		}
		return buffer.toString();
	}

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 * 
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static Class[] getClasses(String packageName) {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources;
		try {
			resources = classLoader.getResources(path);
			List<File> dirs = new ArrayList<File>();
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				dirs.add(new File(resource.getFile()));
			}
			ArrayList<Class> classes = new ArrayList<Class>();
			for (File directory : dirs) {
				classes.addAll(findClasses(directory, packageName));
			}
			return classes.toArray(new Class[classes.size()]);
		} catch (Exception e) {
			handleBeanException(e);
		}
		return null;

	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 * 
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File directory, String packageName) {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		try {
			File[] files = directory.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					assert !file.getName().contains(".");
					classes.addAll(findClasses(file, packageName + "." + file.getName()));
				} else if (file.getName().endsWith(".class")) {
					classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
				}
			}
		} catch (Exception e) {
			handleBeanException(e);
		}

		return classes;
	}

	@SuppressWarnings("rawtypes")
	public static Class getClass(String className) {
		Class<?> forName;
		try {
			forName = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return forName;
	}

	public static Object getInstance(String className) {
		Object object;
		try {
			object = getClass(className).newInstance();
			return object;
		} catch (Exception e) {
			handleBeanException(e);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static Object getInstance(Class entityClass) {

		try {
			return entityClass.newInstance();
		} catch (Exception e) {
			handleBeanException(e);
		}
		return null;
	}

	private static void handleBeanException(Exception exception) {
		if (exception instanceof IllegalAccessException) {
			throw new UtilException("create an instance (other than an array), set or get a field, or invoke a method", exception);
		}
		if (exception instanceof InvocationTargetException) {
			throw new UtilException(exception.getMessage(), exception);
		}
		if (exception instanceof NoSuchMethodException) {
			throw new UtilException("method cannot be found", exception);
		}
		if (exception instanceof IOException) {
			throw new UtilException(exception);
		}
		if (exception instanceof ClassNotFoundException) {
			throw new UtilException("Class Not found", exception);
		}
		throw new RuntimeException(exception);

	}

}

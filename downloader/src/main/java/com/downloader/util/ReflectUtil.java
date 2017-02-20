package com.downloader.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A utility class for reflecting of object.
 * @since 2017/01/15 10:35
 */
final public class ReflectUtil {
	public static Map<String, URLClassLoader> mJars = new HashMap<>();

	/** Release variable **/
	static {
		java.lang.Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				mJars.clear();
				mJars = null;
			}
		}));
	}


	/**
	 * Invoke a method of special object with given args.
	 * @param obj The object for method will invoke.
	 * @param methodName The method will to invoking. 
	 * @param paramTypes The parameter types of method will to invoking.
	 * @param args The args of method will to invoking.
	 * @return Result of method after invoking.
	 */
	public final static Object invokeMethod(Object obj, String methodName, Class<?>[] paramTypes, Object... args)
			throws NoSuchMethodException, SecurityException, IllegalAccessException,
					IllegalArgumentException, InvocationTargetException {
		if (obj == null)
			throw new IllegalArgumentException("The special object for method will invoke is illegal!");
		
		Method method = obj.getClass().getMethod(methodName, paramTypes);
		return method.invoke(obj, args);
	}


	/**
	 * Load a jar with class loader and return a Class object by class name.
	 * @param url Url of jar.
	 * @param name Name for need class.
	 * @return Class by special class name.
	 */
	public final static Class<?> loadJar(URL url, String name) throws MalformedURLException, ClassNotFoundException {
		if (url != null && !mJars.containsKey(url)) {
			URLClassLoader ucl = new URLClassLoader(new URL[]{url});
			mJars.put(url.toString(), ucl);
			return Class.forName(name);
		}

		return (Class<?>) null;
	}
}

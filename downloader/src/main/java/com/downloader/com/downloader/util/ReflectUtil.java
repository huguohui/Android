package com.downloader.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * A utility class for reflecting of object.
 * @since 2017/01/15 10:35
 */
final public class ReflectUtil {
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
}

package com.badsocket.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

class ObjectUtils {
	public static <T> T readObject(File file) throws IOException, ClassNotFoundException {
		T object = null;
		ObjectInputStream inputStream = null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(file));
			object = (T) inputStream.readObject();
		}
		catch (EOFException e) {
			return null;
		}
		finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return object;
	}


	public static void writeObject(Object obj, OutputStream os) throws IOException {
		ObjectOutputStream outputStream = null;
		try {
			outputStream = os instanceof ObjectOutputStream
					? (ObjectOutputStream) os : new ObjectOutputStream(os);
			outputStream.writeObject(obj);
			// 写入一个null用于解决readObject()时抛出java.io.EOFException异常的问题
			outputStream.writeObject(null);
		}
		finally {
			if (outputStream != null) {
				outputStream.flush();
				outputStream.close();
			}
		}
	}


	public static void writeObject(Object obj, File file) throws IOException {
		writeObject(obj, new FileOutputStream(file));
	}
}

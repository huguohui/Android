package com.badsocket.util;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class ObjectUtils {
	public static <T> T readObjectBuildIn(File file) throws IOException, ClassNotFoundException {
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

	public static void writeObjectBuildIn(Object obj, OutputStream os) throws IOException {
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


	private static LinkedBuffer serBuffer = LinkedBuffer.allocate(1024 * 1024);

	private static Map<Class, Schema> cachedSchema = new ConcurrentHashMap<>();

	private static <T> Schema<T> getSchema(Class<T> c) {
		Schema<T> schema;
		if (cachedSchema.containsKey(c)) {
			return cachedSchema.get(c);
		}

		schema = RuntimeSchema.getSchema(c);
		cachedSchema.put(c, schema);
		return schema;
	}

	public static <T> byte[] protostuffSerialize(T obj) {
		byte[] protostuff;
		synchronized(serBuffer) {
			try {
				Schema<T> schema = getSchema((Class<T>) obj.getClass());
				protostuff = ProtostuffIOUtil.toByteArray(obj, schema, serBuffer);
			}
			finally {
				serBuffer.clear();
			}
		}
		return protostuff;
	}

	public static <T> T protostuffDeserialize(Class<T> clazz, byte[] data) {
		Schema<T> schema = getSchema(clazz);
		T newMessage = schema.newMessage();
		ProtostuffIOUtil.mergeFrom(data, newMessage, schema);
		return newMessage;
	}

	public static <T> T readObject(Class<T> c, byte[] bytes) throws IOException {
		return protostuffDeserialize(c, bytes);
	}

	public static <T> T readObject(Class<T> c, FileInputStream is, boolean closeStream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buff = new byte[4096];
		int read = 0;
		while((read = is.read(buff)) != -1) {
			baos.write(buff, 0, read);
		}
		if (closeStream) {
			is.close();
		}

		T obj = protostuffDeserialize(c, baos.toByteArray());
		baos = null;
		return obj;
	}

	public static void writeObject(Object obj, OutputStream os, boolean closeStream) throws IOException{
		byte[] serBytes = protostuffSerialize(obj);
		os.write(serBytes);

		if (closeStream) {
			os.flush();
			os.close();
		}
	}

	public static void writeObject(Object obj, File file) throws IOException {
		writeObject(obj, new FileOutputStream(file), true);
	}
}

package com.downloader.manager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Defines some operations that to implementing management operation for example :
 * List, delete, add, modify etc.
 * @since 2015/12/14
 */
public interface Manageable<T> extends Iterable<T> {
	/**
	 * Search a object.
	 * @param sf A search condition of object will be searched.
	 * @return If searched had result list else null.
	 */
	public abstract List<T> search(SearchFilter sf);


	/**
	 * Get a list that inArray all managed objects.
	 * @return A list that inArray all managed objects.
	 */
	public abstract List<T> getList();


	/**
	 * Delete a object.
	 * @param obj The object to deleting.
	 * @return If deleted true else false.
	 */
	boolean delete(T obj) throws Throwable;


	/**
	 * Delete a object.
	 * @param idx The index of object will to deleting.
	 * @return If deleted true else false.
	 */
	boolean delete(int idx) throws Throwable;


	/**
	 * Delete a object.
	 * @return If deleted true else false.
	 */
	void deleteAll() throws Throwable;


	/**
	 * To create something for managing.
	 * @param descriptor Data for creating.
	 * @return true for success, false for fail.
	 */
	T create(AbstractDescriptor descriptor) throws Throwable;


	/**
	 * Filter of searching.
	 */
	public interface SearchFilter {
		/**
		 * Run filter on given data.
		 * @param data Given data.
		 * @return true on filte pass, false on fail.
		 */
		boolean filter(String data);
	}
}

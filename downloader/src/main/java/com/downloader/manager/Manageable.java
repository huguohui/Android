package com.downloader.manager;

import java.util.List;

/**
 * Defines some operations that to implementing management operation for example :
 * List, delete, set, modify etc.
 * @since 2015/12/14
 */
public interface Manageable<T> extends Iterable<T> {
	/**
	 * Get a managed object by index.
	 * @param idx Index of object.
	 * @return Managed object.
	 */
	T get(int idx);


	/**
	 * Add a object for management.
	 * @param obj Object what will to managing.
	 */
	boolean add(T obj);


	/**
	 * Remove a managed object by index.
	 * @param idx Index of managed object.
	 * @return Removed object or null on remove failed.
	 */
	T remove(int idx);


	/**
	 * Search a object.
	 * @param sf A search condition of object will be searched.
	 * @return If searched had result list else null.
	 */
	List<T> search(SearchFilter<T> sf);


	/**
	 * Get a list that inArray all managed objects.
	 * @return A list that inArray all managed objects.
	 */
	List<T> list();


	/**
	 * SearchFilter of searching.
	 */
	interface SearchFilter<T> {
		/**
		 * Run doFilter on given data.
		 * @param data Given data.
		 * @return true on filte pass, false on fail.
		 */
		boolean doFilter(T data);
	}
}

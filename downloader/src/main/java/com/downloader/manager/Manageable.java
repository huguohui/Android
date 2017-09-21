package com.downloader.manager;

import java.util.List;

/**
 * Defines some operations that to implementing management operation for example :
 * List, delete, add, modify etc.
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
	List<T> getList();


	/**
	 * Filter of searching.
	 */
	public interface SearchFilter<T> {
		/**
		 * Run filter on given data.
		 * @param data Given data.
		 * @return true on filte pass, false on fail.
		 */
		boolean filter(T data);
	}
}

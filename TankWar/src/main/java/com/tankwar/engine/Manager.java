package com.tankwar.engine;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Defines some operations that to implementing management operation for example :
 * List, delete, add, modify etc.
 * @since 2015/12/14
 */
public interface Manager<T> extends Iterator<T> {
	/**
	 * Search a object.
	 * @param schstr A search condition of object will be searched.
	 * @return If searched had result list else null.
	 */
	List<T> search(String schstr);


	/**
	 * Get a list that contains all managed objects.
	 * @return A list that contains all managed objects.
	 */
	List<T> getList();


	/**
	 * Delete a object.
	 * @param obj The object to deleting.
	 * @return If deleted true else false.
	 */
	boolean delete(T obj);


	/**
	 * Add a object to management list.
	 * @param obj The object will add to list.
	 */
	void add(T obj);


	/**
	 * Get a details of object.
	 * @return A details of object.
	 */
	T getByName(String name);


	/**
	 * Add all object to managed list.
	 * @param list Object of needing to management.
	 */
	void addAll(List<T> list);
}

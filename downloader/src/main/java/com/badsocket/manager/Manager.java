package com.badsocket.manager;

import java.util.Collection;
import java.util.List;

/**
 * Defines some operations that to implementing management operation for example :
 * List, delete, set, modify etc.
 * @since 2015/12/14
 */
public interface Manager<T> extends Iterable<T> {
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
	boolean add(T obj) throws Exception;


	/**
	 * Add all object to list.
	 * @param objs All objects.
	 * @return ture for successing
	 */
	boolean addAll(Collection<? extends T> objs);



	/**
	 * Set position in manager list.
	 * @param o Object will change position.
	 * @param i position will set.
	 * @return true success else fail.
	 */
	void move(T o, int i);


	/**
	 * Replace position in manager list.
	 * @param o First object.
	 * @param e Second object.
	 * @return true success else fail.
	 */
	void replace(T o, T e);


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
		 * @return true on doFrontCheck pass, false on fail.
		 */
		boolean doFilter(T data);
	}
}

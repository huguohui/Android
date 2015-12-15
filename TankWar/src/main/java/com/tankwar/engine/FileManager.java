package com.tankwar.engine;

import java.util.List;
import java.util.Map;

/**
 * File manager is a kind of manager that to manage file on disk.
 * The manager can move, cut, copy and delete file.
 * @since 2015/12/15
 */
public class FileManager extends AbstractManager<String> {
	/**
	 * Search a object.
	 *
	 * @param schstr A search condition of object will be searched.
	 * @return If searched had result list else null.
	 */
	@Override
	public List<String> search(String schstr) {
		return null;
	}

	/**
	 * Get a list that contains all managed objects.
	 *
	 * @return A list that contains all managed objects.
	 */
	@Override
	public List<String> getList() {
		return null;
	}

	/**
	 * Delete a object.
	 *
	 * @param obj The object to deleting.
	 * @return If deleted true else false.
	 */
	@Override
	public boolean delete(String obj) {
		return false;
	}

	/**
	 * Add a object to management list.
	 *
	 * @param obj The object will add to list.
	 */
	@Override
	public void add(String obj) {

	}

	/**
	 * Get a details of object.
	 *
	 * @param obj
	 * @return A details of object.
	 */
	@Override
	public Map getDetails(Object obj) {
		return null;
	}

	/**
	 * Add all object to managed list.
	 *
	 * @param list Object of needing to management.
	 */
	@Override
	public void addAll(List<String> list) {

	}
}

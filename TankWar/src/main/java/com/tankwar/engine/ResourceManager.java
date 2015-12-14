package com.tankwar.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The game resource manager.
 * @since 2015/12/14
 */
public class ResourceManager extends Manager {
	/** The all object of managed. */
	private Map<String, String> mResources = new HashMap<>();


	/**
	 * Search a object.
	 *
	 * @param schstr A search condition of object will be searched.
	 * @return If searched had result list else null.
	 */
	@Override
	public List search(String schstr) {
		return null;
	}

	/**
	 * Get a list that contains all managed objects.
	 *
	 * @return A list that contains all managed objects.
	 */
	@Override
	public List getList() {
		return null;
	}

	/**
	 * Delete a object.
	 *
	 * @param obj The object to deleting.
	 * @return If deleted true else false.
	 */
	@Override
	public boolean delete(Object obj) {
		return false;
	}

	/**
	 * Add a object to management list.
	 *
	 * @param obj The object will add to list.
	 */
	@Override
	public void add(Object obj) {

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
}

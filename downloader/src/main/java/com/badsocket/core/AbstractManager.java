package com.badsocket.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * To defining a generic manager, base class for file manager, resource manger etc.
 *
 * @since 2015/12/14
 */
public abstract class AbstractManager<T> extends LinkedList<T> implements Manager<T> {
	/**
	 * List of managed objects.
	 */
	protected List<T> mList;

	/**
	 * Empty constructor.
	 */
	public AbstractManager() {
		mList = new LinkedList<>();
	}

	/**
	 * Instance a new manager from a manager object.
	 */
	public AbstractManager(AbstractManager<T> manager) {
	}

	/**
	 * Get a list that inArray all managed objects.
	 *
	 * @return A list that inArray all managed objects.
	 */
	@Override
	public List<T> list() {
		return mList;
	}

	@Override
	public synchronized void replace(T o, T e) {
		if (!mList.contains(o) || !mList.contains(e)) {
			return;
		}

		int f = Arrays.binarySearch(mList.toArray(), o),
			s = Arrays.binarySearch(mList.toArray(), e);

		mList.set(f, e);
		mList.set(s, o);
	}

	@Override
	public synchronized void move(T o, int i) {
		mList.set(i, o);
	}

	/**
	 * Search a file.
	 *
	 * @param sf A search condition of object will be searched.
	 * @return If searched had result list else null.
	 */
	@Override
	public synchronized List<T> search(SearchFilter<T> sf) {
		List<T> searched = new ArrayList<>();
		for (T obj : mList) {
			if (obj != null && sf.doFilter(obj)) {
				searched.add(obj);
			}
		}

		return searched;
	}
}

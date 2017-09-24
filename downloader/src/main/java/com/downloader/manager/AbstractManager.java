package com.downloader.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * To defining a generic manager, base class for file manager, resource manger etc.
 * @since 2015/12/14
 */
public abstract class AbstractManager<T> implements Manageable<T> {
	/**
     * List of managed objects.
     */
    protected LinkedList<T> mList;


    /**
     * Empty constructor.
     */
    public AbstractManager() {
        mList = new LinkedList<>();
    }


    /**
     * Instance a new manager from a manager object.
     */
    public AbstractManager(AbstractManager<T> manager) { }


    /**
     * Get a managed object by index.
     *
     * @param idx Index of object.
     * @return Managed object.
     */
    @Override
    public T get(int idx) {
        return mList.get(idx);
    }


    /**
     * Add a object for management.
     *
     * @param obj Object what will to managing.
     */
    @Override
    public boolean add(T obj) {
        return mList.add(obj);
    }


    /**
     * Remove a managed object by index.
     *
     * @param idx Index of managed object.
     * @return Removed object or null on remove failed.
     */
    @Override
    public T remove(int idx) {
        return mList.remove(idx);
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


    /**
     * Search a file.
     *
     * @param sf A search condition of object will be searched.
     * @return If searched had result list else null.
     */
    @Override
    public synchronized List<T> filter(Filter<T> sf) {
        List<T> searched = new ArrayList<>();
        for (T obj : mList) {
            if (obj != null && sf.doFilter(obj)) {
                searched.add(obj);
            }
        }

        return searched;
    }


    @Override
    public Iterator<T> iterator() {
        return null;
    }
}

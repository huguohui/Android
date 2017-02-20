package com.downloader.manager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * To defining a generic manager, base class for file manager, resource manger etc.
 * @since 2015/12/14
 */
public abstract class AbstractManager<T> implements Manageable<T> {
    /**
     * Empty constructor.
     */
    public AbstractManager() { }


    /**
     * Instance a new manager from a manager object.
     */
    public AbstractManager(AbstractManager<T> manager) { }
}

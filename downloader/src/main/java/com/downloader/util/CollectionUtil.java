package com.downloader.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class CollectionUtil {

	public static <T> void forEach(Collection<T> c, Callback<T> callbck) {
		for (Iterator<T> t = c.iterator(); t.hasNext(); ) {
			callbck.callback(t.next());
		}
	}


	public static <T> void forEach(Collection<T> c, Action<T> action) {
		for (Iterator<T> t = c.iterator(); t.hasNext(); ) {
			action.doAction(t.next());
		}
	}


	public static <T> List<T> filter(Collection<T> c, Filter<T> f) {
		List<T> list = new ArrayList<>();
		for (Iterator<T> t = c.iterator(); t.hasNext(); ) {
			T o = t.next();
			if (f.filter(o)) {
				list.add(o);
			}
		}

		return list;
	}


	public interface Action<T> {
		void doAction(T o);
	}


	public interface Callback<T> {
		void callback(T o);
	}


	public interface Filter<T> {
		boolean filter(T o);
	}
}

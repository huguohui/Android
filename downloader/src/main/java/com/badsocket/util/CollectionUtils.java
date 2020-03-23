package com.badsocket.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class CollectionUtils {

	public static <T> void forEach(@NonNull T[] os, Callback<T> callbck) {
		for (T o : os) {
			callbck.callback(o);
		}
	}

	public static <T> void forEach(@NonNull T[] os, Action<T> callbck) {
		for (T o : os) {
			callbck.doAction(o);
		}
	}

	public static <T> void forEach(@NonNull Collection<T> c, Callback<T> callbck) {
		for (Iterator<T> t = c.iterator(); t.hasNext(); ) {
			callbck.callback(t.next());
		}
	}

	public static <T> void forEach(@NonNull Collection<T> c, Action<T> action) {
		for (Iterator<T> t = c.iterator(); t.hasNext(); ) {
			action.doAction(t.next());
		}
	}

	@NonNull
	public static <T> List<T> filter(@NonNull Collection<T> c, Filter<T> f) {
		List<T> list = new ArrayList<>();
		for (Iterator<T> t = c.iterator(); t.hasNext(); ) {
			T o = t.next();
			if (f.filter(o)) {
				list.add(o);
			}
		}

		return list;
	}

	@FunctionalInterface
	public interface Action<T> {
		void doAction(T o);
	}

	@FunctionalInterface
	public static interface Callback<T> {
		void callback(T o);
	}

	@FunctionalInterface
	public static interface Filter<T> {
		boolean filter(T o);
	}
}

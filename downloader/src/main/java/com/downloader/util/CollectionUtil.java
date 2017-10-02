package com.downloader.util;

import java.util.Collection;
import java.util.Iterator;

public abstract class CollectionUtil {

	public static <T> void forEach(Collection<T> c, Callback<T> callbck) {
		for (Iterator<T> t = c.iterator(); t.hasNext(); ) {
			callbck.callback(t.next());
		}
	}


	public interface Callback<T> {
		void callback(T o);
	}
}

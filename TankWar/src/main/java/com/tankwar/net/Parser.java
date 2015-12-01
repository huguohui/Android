package com.tankwar.net;

/**
 * Parser data from data.
 * @since 2015/12/01
 */
public interface Parser<T, E> {
	/**
	 * Parser data to a kind of format.
	 * @param data Provided data.
	 * @return Some data.
	 */
	T parse(E data);
}

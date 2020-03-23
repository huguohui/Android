package com.badsocket.core;

/**
 * Created by skyrim on 2017/12/17.
 */

public enum NetworkType {

	NT_2G("2G"),

	NT_3G("3G"),

	NT_4G("4G"),

	NT_5G("5G"),

	NT_WIFI("WI-FI");

	private String type;

	private NetworkType(String type) {
		this.type = type;
	}

}

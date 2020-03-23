package com.badsocket.core;

/**
 * Common protocol.
 */
public enum Protocols {

	/**
	 * HyperText Transfer Protocols.
	 */
	HTTP,

	/**
	 * Hyper Text Transfer Protocols over Secure Socket Layer.
	 */
	HTTPS,

	/**
	 * File Transfer Protocols.
	 */
	FTP,

	MAGANET,

	ED2K;

	public static Protocols getProtocol(String protocolName) {
		try {
			return valueOf(protocolName.toUpperCase());
		}
		catch (IllegalArgumentException e) {
			return null;
		}
	}

}

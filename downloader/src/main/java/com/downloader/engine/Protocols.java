package com.downloader.engine;

/**
 * Common protocol. 
 */
public enum Protocols {
	/** HyperText Transfer Protocols. */
	HTTP,

	/** Hyper Text Transfer Protocols over Secure Socket Layer. */
	HTTPS,
	
	/** File Transfer Protocols. */
	FTP,

	MAGANET,

	ED2K;


	public static boolean isSupport(String ptl) {
		Protocols protocol = null;
		try {
			protocol = Protocols.valueOf(ptl.toUpperCase());
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}


	public static Protocols getProtocol(String name) {
		return Protocols.valueOf(name.toUpperCase());
	}
}

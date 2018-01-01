package com.badsocket.core;

/**
 * Common protocol. 
 */
public enum Protocol {

	/** HyperText Transfer Protocol. */
	HTTP,

	/** Hyper Text Transfer Protocol over Secure Socket Layer. */
	HTTPS,
	
	/** File Transfer Protocol. */
	FTP,

	MAGANET,

	ED2K;


	public static Protocol getProtocol(String protocolName) {
		try {
			return valueOf(protocolName.toUpperCase());
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}

}

package com.downloader.client.downloader;

/**
 * Common protocol. 
 */
public enum SupportedProtocol {
	/** HyperText Transfer Protocol. */
	HTTP,

	/** Hyper Text Transfer Protocol over Secure Socket Layer. */
	HTTPS,
	
	/** File Transfer Protocol. */
	FTP;

	public boolean isSupport(String ptl) {
		Enum<SupportedProtocol> protocol = null;
		try {
			protocol = SupportedProtocol.valueOf(ptl);
		}
		catch (IllegalArgumentException e) {
			return false;
		}

		for (SupportedProtocol sp : SupportedProtocol.values()) {
			if (sp.equals(protocol))
				return true;
		}

		return false;
	}
}

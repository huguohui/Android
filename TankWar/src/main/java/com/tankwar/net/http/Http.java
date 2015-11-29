package com.tankwar.net.http;

/**
 * Describe a Hypertext transfer protocol.
 * @author HGH
 * @since 2015/11/05
 */
public abstract class Http {
    /** The http message line break. */
    public final static String CRLF = "\r\n";

	/** The http protocol name. */
	public final static String PROTOCOL = "HTTP";

	/** Enum of support request method. */
	public static enum Method {
		GET,  //GET request.
		POST, //POST request.
		HEAD, //HEAD request.
	};

	/**
	 * MIME types.
	 */
	public interface MIME {
		public final static String JPEG = "image/jpeg";
	}
}

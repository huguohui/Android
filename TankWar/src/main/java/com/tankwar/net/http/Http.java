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

	/** The header content space. */
	public final static String SPACE = " ";
	
	/** The default port of http. */
	public final static int DEFAULT_PORT = 80;

	/** Enum of support request method. */
	public enum Method {
		GET,  //GET request.
		POST, //POST request.
		HEAD, //HEAD request.
	}


	/** HTTP headers. */
	public final static String ACCEPT 				= "Accept";
	public final static String ACCEPT_ENCODING		= "Accept-Encoding";
	public final static String ACCEPT_LANGUAGE		= "Accept-Language";
	public final static String ACCEPT_CHARSET		= "Accept-Charset";
	public final static String HOST 				= "Host";
	public final static String CONNECTION 			= "Connection";
	public final static String USER_AGENT 			= "User-Agent";
	public final static String DATE 				= "Date";
	public final static String FROM 				= "From";
	public final static String PRAGMA 				= "Pragma";
	public final static String REFERER				= "Referer";
	public final static String RANGE				= "Range";
	public final static String CACHE_CONTROL		= "Cache-Control";
	public final static String ETAG					= "Etag";
	public final static String LAST_MODIFIED		= "Last-Modified";
	public final static String COOKIE				= "Cookie";
	public final static String SET_COOKIE			= "Set-Cookie";
	public final static String EXPIRES				= "Expires";
	public final static String LOCATION				= "Location";
	public final static String REFRESH				= "Refresh";
	public final static String WWW_AUTHENTICATE		= "WWW-Authenticate";
	public final static String TRANSFER_ENCODING	= "Transfer-Encoding";
	public final static String AUTHORIZATION		= "Authorization";
	public final static String EXPECT				= "Expect";
	public final static String IF_MATCH				= "If-Match";
	public final static String IF_MODIFIED_SINCE	= "If-Modified-Since";
	public final static String IF_NONE_MATCH		= "If-None_Match";
	public final static String IF_RANGE				= "If-Range";
	public final static String IF_UNMODIFIED_SINCE	= "If-Unmodified-Since";
	public final static String TE					= "TE";
	public final static String ACCEPT_RANGE			= "Accept-Range";
	public final static String AGE					= "Age";
	public final static String VARY					= "Vary";
	public final static String ALLOW				= "Allow";
	public final static String CONTENT_LENGTH		= "Content-Length";
	public final static String CONTENT_TYPE			= "Content-Type";
	public final static String CONTENT_RANGE		= "Content-Range";
	public final static String CONTENT_ENCODING		= "Content-Encoding";
	public final static String CONTENT_LANGUAGE		= "Content-Language";
	public final static String CONTENT_LOCATION		= "Content-Location";
	public final static String CONTENT_MD5			= "Content-MD5";
	public final static String CONTENT_DISPOSITION	= "Content-Disposition";

	/**
	 * MIME types.
	 */
	public interface MIME {
		String JPEG = "image/jpeg";
	}
}

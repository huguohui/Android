package com.badsocket.net.newidea;

import java.io.Serializable;

/**
 * The Uniform resource identifier.
 */
public class URI implements Serializable {
	/**
	 * The host of URI.
	 */
	private String host = "";

	/**
	 * The scheme of URI.
	 */
	private String scheme = "";

	/**
	 * The port of URI.
	 */
	private Integer port = -1;

	/**
	 * The user of URI.
	 */
	private String user = "";

	/**
	 * The password of URI.
	 */
	private String password = "";

	/**
	 * The path of URI.
	 */
	private String path = "/";

	/**
	 * The search part of URI
	 */
	private String query = "";

	/**
	 * The hash part of URI
	 */
	private String fragment = "";

	private String originUri;

	private StringBuilder parsingUri = new StringBuilder();

	private String specific = "";

	private boolean isOpaque;

	private final char[] RESERVED_CHARACTERS = {
			';', '/', '?', ':', '@', '&', '=', '+', '$', ','
	};

	private final String COLON = ":";

	private final String SLASH = "/";

	private final String DOUBLE_SLASH = "//";

	private final String AT_CHAR = "@";

	private final String QUESTION = "?";

	private final String HASH = "#";

	public URI(String uriStr) {
		originUri = uriStr.trim();
		parsingUri.append(originUri);
		parseURI();
	}

	private void parseURI() {
		extractScheme();
		extractSchemeSpecificPart();
	}

	private void extractScheme() {
		int colonIndex = parsingUri.indexOf(COLON);
		if (colonIndex != -1) {
			scheme = parsingUri.substring(0, colonIndex);
			parsingUri.delete(0, colonIndex + COLON.length());
		}
	}

	private void extractSchemeSpecificPart() {
		if (parsingUri.length() == 0) {
			return;
		}

		if (parsingUri.indexOf(SLASH) != -1)
			parseHierarchicalPart();
		else {
			isOpaque = true;
			parseOpaquePart();
		}
	}

	private void parseHierarchicalPart() {
		if (parsingUri.indexOf(DOUBLE_SLASH) == 0) {
			parsingUri.delete(0, DOUBLE_SLASH.length());
			parseAuthorityComponent();
		}

		parsePathComponent();
		parseQueryComponent();
		parseHashComponent();
	}

	private void parseAuthorityComponent() {
		int slashIndex = parsingUri.indexOf(SLASH);
		String authroity = parsingUri.substring(0, Math.max(0, slashIndex));
		String hostAndPort = "";

		if (!authroity.isEmpty()) {
			hostAndPort = authroity;
			int atCharIndex = authroity.indexOf(AT_CHAR);
			if (atCharIndex != -1) {
				String userInfo = authroity.substring(0, atCharIndex);
				int colonIndex = userInfo.indexOf(COLON);
				user = userInfo;
				if (colonIndex != -1) {
					user = userInfo.substring(0, colonIndex);
					if (colonIndex != userInfo.length() - COLON.length()) {
						password = userInfo.substring(colonIndex + COLON.length());
					}
				}

				hostAndPort = authroity.substring(atCharIndex + AT_CHAR.length());
			}

			parsingUri.delete(0, slashIndex);
		}

		host = hostAndPort;
		int colonIndex = hostAndPort.indexOf(COLON);
		if (colonIndex != -1) {
			host = hostAndPort.substring(0, colonIndex);
			String port = hostAndPort.substring(colonIndex + COLON.length());
			if (!port.isEmpty()) {
				try {
					this.port = Integer.parseInt(port);
				}
				catch (Exception e) {
				}
			}
		}
	}

	private void parsePathComponent() {
		if (parsingUri.length() != 0) {
			path = parsingUri.toString();
			int qmIndex = parsingUri.indexOf(QUESTION),
				hashIdx = parsingUri.indexOf(HASH),
				pathEndIndex = parsingUri.length();
			if (qmIndex != -1) {
				path = parsingUri.substring(0, qmIndex);
				pathEndIndex = qmIndex;
			}
			else if (hashIdx != -1) {
				path = parsingUri.substring(0, hashIdx);
				pathEndIndex = hashIdx;
			}
			if (path.isEmpty()) {
				path = SLASH;
			}

			parsingUri.delete(0, pathEndIndex);
		}
	}

	private void parseQueryComponent() {
		int questionIdx = parsingUri.indexOf(QUESTION);
		int hashIdx = parsingUri.indexOf(HASH);
		if (parsingUri.length() == 0 || questionIdx == -1 || questionIdx > hashIdx) {
			return;
		}

		query = parsingUri.toString();
		if (hashIdx != -1) {
			query = parsingUri.substring(0, hashIdx);
			parsingUri.delete(0, hashIdx + HASH.length());
		}
	}

	private void parseHashComponent() {
		fragment = parsingUri.toString().replace("#", "");
	}

	private void parseOpaquePart() {
		specific = parsingUri.toString();
	}

	public String getHost() {
		return host;
	}

	public String getScheme() {
		return scheme;
	}

	public Integer getPort() {
		return port;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getPath() {
		return path;
	}

	public String getQuery() {
		return query;
	}

	public String getFragment() {
		return fragment;
	}

	public String getOriginUri() {
		return originUri;
	}

	public String getSpecific() {
		return specific;
	}

	public boolean isOpaque() {
		return isOpaque;
	}

	public URI setOpaque(boolean opaque) {
		isOpaque = opaque;
		return this;
	}

	public String toString() {
		return originUri;
	}

}

package com.badsocket.net.newidea;

/**
 * The Uniform resource identifier.
 */
public class URI {
	/** The host of URI. */
	private String host;

	/** The scheme of URI. */
	private String scheme;

	/** The port of URI. */
	private Integer port;

	/** The user of URI. */
	private String user;

	/** The password of URI. */
	private String password;

	/** The path of URI. */
	private String path;

	/** The search part of URI */
	private String search;

	/** The hash part of URI */
	private String hash;

	public URI(String uriStr) {

	}

	private void parseURI(String str) throws InvalidUriException {
		str = str.trim();
		if (!str.contains("://")) {
			throw new InvalidUriException();
		}

		str = extractScheme(str);

	}

	private String extractScheme(String str) throws InvalidUriException {
		int index = 0;
		scheme = str.substring(0, index = str.indexOf("://"));
		if (scheme.length() == 0) {
			throw new InvalidUriException("Invalid scheme");
		}
		return str.substring(index + 3);
	}

	private String extractHostPart(String str) throws InvalidUriException {
		int index = 0;
		if ((index = str.indexOf("@")) != -1) {

		}
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public String getScheme() {
		return scheme;
	}


	public void setScheme(String scheme) {
		this.scheme = scheme;
	}


	public Integer getPort() {
		return port;
	}


	public void setPort(Integer port) {
		this.port = port;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}


	public String getHash() {
		return hash;
	}


	public void setHash(String hash) {
		this.hash = hash;
	}
}

package com.badsocket.util;

import com.badsocket.net.http.Http;
import com.badsocket.net.newidea.URI;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * Utility for url.
 *
 * @since 2017/01/15 16:19
 */
final public class URLUtils {
	/**
	 * Get the top domain of url.
	 *
	 * @param url Given url.
	 * @return Top domain of url.
	 */
	public static String topDomain(URI url) {
		if (url == null)
			return null;

		String host = url.getHost();
		return host.substring(host.substring(0, host.lastIndexOf('.')).lastIndexOf('.') + 1);
	}

	/**
	 * Get IP downloadAddress by top domain.
	 *
	 * @param domain Top domain.
	 * @return IP downloadAddress of domain.
	 * @throws UnknownHostException Can't parse domain.
	 */
	public static InetAddress inetAddressByDomain(String domain) throws UnknownHostException {
		return InetAddress.getByName(domain);
	}

	/**
	 * Get socket downloadAddress by URI.
	 *
	 * @param url The URI.
	 * @return Socket downloadAddress.
	 * @throws UnknownHostException
	 */
	public static InetSocketAddress socketAddressByUri(URI url) throws UnknownHostException {
		return new InetSocketAddress(inetAddressByDomain(url.getHost()),
				url.getPort() == -1 ? 80 : url.getPort());
	}

	/**
	 * Get URI path and query string.
	 *
	 * @param url Given url.
	 * @return URI path and query string;
	 */
	public static String uriFullPathParam(URI url) {
		if (url == null) return null;
		return (url.getPath() == null || url.getPath().equals("") ?
				"/" + url.getPath() : url.getPath())
				+ (url.getQuery() == null ? "" : "?" + url.getQuery())
				+ (url.getFragment() == null ? "" : "#" + url.getFragment());
	}

	/**
	 * Get domain with port.
	 *
	 * @param url Given url.
	 * @return domain with port.
	 */
	public static String domainWithPort(URI url) {
		if (url == null) return null;
		return url.getHost() + ":" + (url.getPort() != -1 ? url.getPort() : Http.DEFAULT_PORT);
	}

	/**
	 * Get filename of uri.
	 *
	 * @param uri Given uri.
	 * @return Filename of uri.
	 */
	public static String filename(URI uri) {
		if (uri == null) return null;
		String file = uri.getPath();
		String[] path = file.split("/");
		if (file.trim().length() == 0)
			return "index.html";

		if (path.length != 0) {
			try {
				return URLDecoder.decode(path[path.length - 1], "UTF-8");
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return file;
	}

	/**
	 * Get string url by URI.
	 *
	 * @param url URI object.
	 * @return String url.
	 */
	public static String toString(URI url) {
		return url.getScheme() + "://" + url.getHost() + ':' + (url.getPort() == -1 ? "80" : url.getPort())
				+ uriFullPathParam(url);
	}

	/**
	 * Get string url by URI without path.
	 *
	 * @param url URI object.
	 * @reutrn String url without path.
	 */
	public static String urlWithoutPath(URI url) {
		return url.getScheme() + "://" + url.getHost() + ':' + (url.getPort() == -1 ? "80" : url.getPort());
	}

	/**
	 * 以一个基本的URI为基础，根据给出的部分URI组建一个完整的URI，如果给出的部分URI为完整的URI，则原样返回。
	 *
	 * @param baseUrl 基本的URI。
	 * @param url     部分URI。
	 * @return 一个完整的URI。
	 * @throws MalformedURIException
	 */
	public static URI fullURI(URI baseUrl, String url) throws MalformedURLException {
		final String urlRegex = "^https?://(\\w+\\.)+([a-z]{2,5})(:\\d+)?(/.*)?";
		Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
		if (pattern.matcher(url).matches())
			return new URI(url);

		String baseUrlStr = urlWithoutPath(baseUrl), newPath = "", newUrl = "";
		if (url.startsWith("/")) {
			newUrl = baseUrlStr + url;
		}
		else {
			String p = baseUrl.getPath();
			int offset = p.lastIndexOf("/");
			newPath = p.endsWith("/") ? p : (offset == -1 ? "/" : p.substring(0, offset + 1));
			newUrl = baseUrlStr + newPath + url;
		}

		return new URI(newUrl);
	}

	public static String decode(String url, String enc) {
		try {
			return URLDecoder.decode(url, enc);
		}
		catch (UnsupportedEncodingException ue) {
			ue.printStackTrace();
		}

		return "";
	}

	public static String encode(String url, String enc) {
		try {
			return URLEncoder.encode(url, enc);
		}
		catch (UnsupportedEncodingException ue) {
			ue.printStackTrace();
		}

		return "";
	}

	public static void main(String[] args) throws MalformedURLException {
/*
		String downloadAddress = "http://www.app.baidu.com:80/asdfasdfaa;fa/?afja9f1#afk?";
		final String urlRegex = "^https?://(\\w+\\.)+([a-z]{2,5})(:\\d+)?(/.*)?";
		Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
		if (pattern.matcher(downloadAddress).matches()) {
			System.out.println(downloadAddress);
		}
*/

		System.out.println(fullURI(new URI("http://baidu.com/dfa/a"), "../"));
	}
}

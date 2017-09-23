package com.downloader.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import com.downloader.net.http.Http;


/**
 * Utility for url.
 * @since 2017/01/15 16:19
 */
final public class UrlUtil {
	/**
	 * Get the top domain of url.
	 * @param url Given url.
	 * @return Top domain of url.
	 */
	public static String getTopDomain(URL url) {
		if (url == null)
			return null;

		String host = url.getHost();
		return "www." + host.substring(host.substring(0, host.lastIndexOf('.')).lastIndexOf('.') + 1);
	}


	/**
	 * Get IP address by top domain.
	 * @param domain Top domain.
	 * @return IP address of domain.
	 * @throws UnknownHostException Can't parse domain.
	 */
	public static InetAddress getInetAddressByDomain(String domain) throws UnknownHostException {
		return InetAddress.getByName(domain);
	}


	/**
	 * Get socket address by URL.
	 * @param url The URL.
	 * @return Socket address.
	 * @throws UnknownHostException
	 */
	public static InetSocketAddress getSocketAddressByUrl(URL url) throws UnknownHostException {
		return new InetSocketAddress(getInetAddressByDomain(url.getHost()),
				url.getPort() == -1 ? 80 : url.getPort());
	}

	
	/**
	 * Get URL path and query string.
	 * @param url Given url.
	 * @return URL path and query string;
	 */
	public static String getUrlFullPath(URL url) {
		if (url == null) return null;
		return (url.getPath() == null || url.getPath().equals("") ?
				"/" + url.getPath() : url.getPath())
				+ (url.getQuery() == null ? "" : "?" + url.getQuery())
				+ (url.getRef() == null ? "" : "#" +url.getRef());
	}
	
	
	/**
	 * Get domain with port.
	 * @param url Given url.
	 * @return domain with port.
	 */
	public static String getDomainWithPort(URL url) {
		if (url == null) return null;
		return url.getHost() + ":" + (url.getPort() != -1 ? url.getPort() : Http.DEFAULT_PORT);
	}
	
	
	/**
	 * Get filename of url.
	 * @param url Given url.
	 * @return Filename of url.
	 */
	public static String getFilename(URL url) {
		if (url == null) return null;
		String file = url.getFile();
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
	 * Get string url by URL.
	 * @param url URL object.
	 * @return String url.	
	 */
	public static String getUrl(URL url) {
		return url.getProtocol() + "://" + url.getHost() + ':' + (url.getPort() == -1 ? "80" : url.getPort())
				+ getUrlFullPath(url);
	}
	
	
	/**
	 * Get string url by URL without path.
	 * @param url URL object.
	 * @reutrn String url without path.
	 */
	public static String getUrlWithoutPath(URL url) {
		return url.getProtocol() + "://" + url.getHost() + ':' + (url.getPort() == -1 ? "80" : url.getPort());
	}
	
	
	/**
	 * 以一个基本的URL为基础，根据给出的部分URL组建一个完整的URL，如果给出的部分URL为完整的URL，则原样返回。
	 * @param baseUrl 基本的URL。
	 * @param url 部分URL。
	 * @return 一个完整的URL。
	 * @throws MalformedURLException 
	 */
	public static URL getFullUrl(URL baseUrl, String url) throws MalformedURLException {
		final String urlRegex = "^https?://(\\w+\\.)+([a-z]{2,5})(:\\d+)?(/.*)?";
		Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
		if (pattern.matcher(url).matches())
			return new URL(url);
		
		String baseUrlStr = getUrlWithoutPath(baseUrl), newPath = "", newUrl = "";
		if (url.startsWith("/")) {
			newUrl = baseUrlStr + url;
		}
		else {
			String p = baseUrl.getPath();
			int offset = p.lastIndexOf("/");
			newPath = p.endsWith("/") ? p : (offset == -1 ? "/" : p.substring(0, offset + 1));
			newUrl = baseUrlStr + newPath + url;
		}

		return new URL(newUrl);
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
		String url = "http://www.app.baidu.com:80/asdfasdfaa;fa/?afja9f1#afk?";
		final String urlRegex = "^https?://(\\w+\\.)+([a-z]{2,5})(:\\d+)?(/.*)?";
		Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
		if (pattern.matcher(url).matches()) {
			System.out.println(url);
		}
*/


		System.out.println(getFullUrl(new URL("http://baidu.com/dfa/a"), "../"));
	}
}

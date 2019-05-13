package com.anti_captcha.Http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HttpRequest {
	private String url;
	private String postRaw;
	private Integer timeout = 60_000; // milliseconds
	private Integer maxBodySize = 0; // 0 = unlimited, in bytes
	private boolean followRedirects = true; // does not work now due to moving
											 // from JSOUP to ApacheHttpClient
	private boolean validateTLSCertificates = false;
	private Map<String, String> proxy = null; // new HashMap<String, String>()
												 // {{put("host",
												 // "192.168.0.168");
												 // put("port", "8888");}};
	private Map<String, String> cookies = new HashMap<>();
	private Map<String, String> headers = new HashMap<String, String>() {
		/**
		 *
		 */
		private static final long serialVersionUID = -3008149882133739333L;

		{
			put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			put("Accept-Encoding", "gzip, deflate, sdch");
			put("Accept-Language", "ru-RU,en;q=0.8,ru;q=0.6");
		}
	};

	private boolean noCache = false;
	private Set<Integer> acceptedHttpCodes = new HashSet<Integer>() {
		/**
		*
		*/
		private static final long serialVersionUID = 2745817636081220449L;

		{
			add(200);
		}
	};

	private String[] urlChangingParts = { "session_id", "sessionid", "timestamp",
	};

	public HttpRequest(String url) {
		this.url = url;
	}

	public void addAcceptedHttpCode(Integer httpCode) {
		acceptedHttpCodes.add(httpCode);
	}

	public void addCookie(String key, String value) {
		cookies.put(key, value);
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public void addToPost(String key, String value) throws UnsupportedEncodingException {
		if (postRaw == null) {
			postRaw = "";
		} else {
			postRaw += "&";
		}

		postRaw += URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
		addHeader("Content-Type", "application/x-www-form-urlencoded");
	}

	public Set<Integer> getAcceptedHttpCodes() {
		return acceptedHttpCodes;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Integer getMaxBodySize() {
		return maxBodySize;
	}

	public Map<String, String> getProxy() {
		return proxy;
	}

	public String getRawPost() {
		return postRaw;
	}

	public String getReferer() {

		if (headers.get("Referer") != null)
			return headers.get("Referer");

		return null;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public String getUrl() {
		return url;
	}

	public String getUrlWithoutChangingParts(String url) throws Exception {

		String newUrl = url = url.toLowerCase();

		for (String partToRemove : urlChangingParts) {

			String[] splitted = newUrl.split(partToRemove);

			if (splitted.length == 1) {
				continue;
			}

			String firstPiece = splitted[0];
			String secondPiece = splitted[1];

			if (splitted.length > 2) {

				String[] splitted2 = new String[splitted.length - 1];
				System.arraycopy(splitted, 1, splitted2, 0, splitted2.length);

				secondPiece = String.join(partToRemove, splitted2);
			}

			Integer breakpointPos = secondPiece.length();

			if (secondPiece.contains("?")) {
				breakpointPos = secondPiece.indexOf("?");
			} else if (secondPiece.contains("&")) {
				breakpointPos = secondPiece.indexOf("&");
			}

			newUrl = firstPiece + secondPiece.substring(breakpointPos);
		}

		if (newUrl.equals(url))
			return newUrl;
		else
			return getUrlWithoutChangingParts(newUrl);
	}

	public boolean isFollowRedirects() {
		return followRedirects;
	}

	public boolean isNoCache() {
		return noCache;
	}

	public boolean isValidateTLSCertificates() {
		return validateTLSCertificates;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}

	public void setMaxBodySize(Integer maxBodySize) {
		this.maxBodySize = maxBodySize;
	}

	public void setNoCache(boolean noCache) {
		this.noCache = noCache;
	}

	public void setProxy(String proxyHost, Integer proxyPort) {
		proxy = new HashMap<>();
		proxy.put("host", proxyHost);
		proxy.put("port", String.valueOf(proxyPort));
	}

	public void setRawPost(String post) {
		postRaw = post;
	}

	public void setReferer(String referer) {
		headers.put("Referer", referer);
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public void setValidateTLSCertificates(boolean validateTLSCertificates) {
		this.validateTLSCertificates = validateTLSCertificates;
	}
}
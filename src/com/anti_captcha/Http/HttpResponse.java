package com.anti_captcha.Http;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;

public class HttpResponse {
	private String body = null;
	private Map<String, String> headers = new HashMap<>();
	private Map<String, String> cookies = new HashMap<>();
	private String charset = null;
	private String contentType = null;
	private Integer httpCode = null;
	private String httpMessage = null;

	public HttpResponse(String body, Map<String, String> headers, Map<String, String> cookies, String charset, String contentType, Integer httpCode, String statusMessage) {

		this.body = body;
		this.headers = headers;
		this.cookies = cookies;
		this.charset = charset;
		this.contentType = contentType;
		this.httpCode = httpCode;
		httpMessage = statusMessage;
	}

	public HttpResponse(String body, org.apache.http.HttpResponse apacheHttpResponse, HttpClientContext apacheHttpContext) {

		this.body = body;

		// HEADERS
		for (Header header : apacheHttpResponse.getAllHeaders()) {
			headers.put(header.getName(), header.getValue());
		}

		// COOKIES
		for (Cookie cookie : apacheHttpContext.getCookieStore().getCookies()) {
			cookies.put(cookie.getName(), cookie.getValue());
		}

		// Content-Type and charset:
		if (headers.get("Content-Type") != null) {
			String[] contentTypeHeaderSplitted = headers.get("Content-Type").split("; charset=");
			contentType = contentTypeHeaderSplitted[0];

			if (contentTypeHeaderSplitted.length > 1) {
				charset = contentTypeHeaderSplitted[1];
			}
		}

		// STATUS CODE & MESSAGE
		httpCode = apacheHttpResponse.getStatusLine().getStatusCode();
		httpMessage = apacheHttpResponse.getStatusLine().getReasonPhrase();
	}

	public String getBody() {
		return body;
	}

	public String getCharset() {
		return charset;
	}

	public String getContentType() {
		return contentType;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Integer getHttpCode() {
		return httpCode;
	}

	public String getHttpMessage() {
		return httpMessage;
	}

}

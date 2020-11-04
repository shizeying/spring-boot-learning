package com.example.utils.config;

public final class RequestBodyHolder {
	private RequestBodyHolder() {
	}
	
	private static ThreadLocal<String> requestBodyHolder = new ThreadLocal<>();
	
	public static void setJsonRequestBody(String jsonRequestBody) {
		requestBodyHolder.set(jsonRequestBody);
	}
	
	public static String getJsonRequestBody() {
		return requestBodyHolder.get();
	}
}

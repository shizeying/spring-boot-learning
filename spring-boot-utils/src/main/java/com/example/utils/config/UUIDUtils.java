package com.example.utils.config;

import java.util.UUID;

public final class UUIDUtils {
	public static String getFullString() {
		return UUID.randomUUID().toString();
	}
	
	public static String getShortString() {
		return getFullString().replace("-", "");
	}
}


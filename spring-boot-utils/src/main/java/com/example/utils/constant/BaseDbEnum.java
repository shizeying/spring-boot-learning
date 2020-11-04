package com.example.utils.constant;

import java.util.Objects;

@SuppressWarnings("ALL")
public interface BaseDbEnum {
	Integer getValue();
	
	String getDisplay();
	
	static <T extends BaseDbEnum> T findByValue(Class<T> enumType, Integer value) {
		for (T object : enumType.getEnumConstants()) {
			if (Objects.equals(value, object.getValue())) {
				return object;
			}
		}
		return null;
	}
}


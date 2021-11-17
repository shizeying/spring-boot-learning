package com.example.jpa.config;

import com.example.utils.config.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.AttributeConverter;

/**
 * jpa json转换器
 *
 * @author shizeying
 * @date 2021/11/16
 */
public class JpaConverterJson implements AttributeConverter<List<Object>, String>, Serializable {
	@Override
	public String convertToDatabaseColumn(List<Object> o) {
		return JacksonUtil.bean2Json(o);
	}
	
	@Override
	public List<Object> convertToEntityAttribute(String s) {
		return JacksonUtil.json2LongByTypeReference(s, new TypeReference<List<Object>>() {
		});
	}
}


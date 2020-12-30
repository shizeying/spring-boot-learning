package com.example.entity;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

/**
 * xml bean
 *
 * @author shizeying
 * @date 2020/12/30
 */
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class XmlBean {
	private String column;
	private String property;
	private String typeHandler;
	
	@Override
	public String toString() {
		return "<result column=\"" + StringUtils.trim(column) + "\" property=\"" + StringUtils.trim(property) + "\"" +
				       (StringUtils.isNotBlank(typeHandler) ? " typeHandler=\"" + StringUtils.trim(typeHandler) + "\"/>" : "/>");
	}
}

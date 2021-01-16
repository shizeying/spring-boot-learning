package com.example.domain;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Builder
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SqlBean {
	private String id;
	private String sql;
	private String resultType;
	private String resultMap="BaseResultMap";
	
	
	@Override
	public String toString() {
		return "<select id=\"" + id + "\" " + (
				StringUtils.isNotBlank(resultType)
						? " resultType=\"" + StringUtils.trim(resultType) + "\"> \n"
						
						: " resultMap=\"" + StringUtils.trim(resultMap) + "\"> \n"
		) + "\n" + sql + "\n" + "</select>";
		
		
	}
}

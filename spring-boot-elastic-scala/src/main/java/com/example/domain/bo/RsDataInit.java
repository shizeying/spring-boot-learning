package com.example.domain.bo;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RsDataInit  implements Serializable {
	private String chineseAlias;
	private String indexPrefix;
	private Map<String, Object> result = Maps.newHashMap();
	
	
}

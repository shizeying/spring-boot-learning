package com.example.domain.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合构建器
 *
 * @author shizeying
 * @date 2021/01/01
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class AggBuilder implements Serializable {
	private List<String> detail;
	/** 类型 */
	private String type;
	/** 列 */
	private String column;
	/** 大小 */
	private String size;
	/** 格式 */
	private String format;
	/** 时间间隔 */
	private String interval;
	private List<String> exclude;
	private List<String> include;
	
}

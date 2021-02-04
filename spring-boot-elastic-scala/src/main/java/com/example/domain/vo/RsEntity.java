package com.example.domain.vo;

import com.example.domain.base.BasicEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * rs实体
 *
 * @author shizeying
 * @date 2021/01/16
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class RsEntity extends BasicEntity {
	private String name;
	private String abs;
	private String origin;
	private String image;
	private String meaningTag;
	private String identifyTag;
	private String lon;
	private String lat;
	private String gisAddress;
	private String startTime;
	private String endTime;
	private String entityTag;
	private String entityType;
	private Map<String, Object> attrMap;
	
}

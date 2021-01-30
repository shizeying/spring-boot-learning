package com.example.domain.vo;

import com.example.domain.base.BasicEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * rs关系
 *
 * @author shizeying
 * @date 2021/01/16
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class RsRelation extends BasicEntity {
	private String entityName;
	private String entityType;
	private String valueName;
	private String valueType;
	private String timeFrom;
	private String timeTo;
	private Map<String, Object> edgeMap;
}

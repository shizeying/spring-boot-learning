package com.example.elasticsearch.entity.bo;

import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;

/**
 * 聚合构建的k
 *
 * @author shizeying
 * @date 2021/01/02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class AttrAggK {
	private Long id;
	private String name;
	private String columnItem;
	private List<Object> detail = Lists.newArrayList();
	private Long parentId;
	private Integer type;
	private Long counts;
	private Object include;
	private Object exclude;
	private Object interval;
	private String format;
	
	
}

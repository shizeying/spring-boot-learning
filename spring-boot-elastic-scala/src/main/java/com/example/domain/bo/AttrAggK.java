package com.example.domain.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import lombok.*;

import java.io.Serializable;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttrAggK  implements Serializable {
	private Long id;
	private String name;
	private String columnItem;
	private List<String> detail = Lists.newArrayList();
	private Long parentId;
	private Integer size;
	private String type;
	private Long counts;
	private List<String> include;
	private List<String> exclude;
	private String interval;
	private String format;
	
	
}

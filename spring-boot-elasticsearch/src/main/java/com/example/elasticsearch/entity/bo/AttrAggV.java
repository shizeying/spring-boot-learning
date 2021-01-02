package com.example.elasticsearch.entity.bo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 构建聚合v
 *
 * @author shizeying
 * @date 2021/01/02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Accessors(chain=true)
public class AttrAggV {
	private Long aggSum;
	private Object value;
	private Object key;
}

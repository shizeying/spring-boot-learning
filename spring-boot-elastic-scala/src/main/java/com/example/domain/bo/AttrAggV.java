package com.example.domain.bo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@Accessors(chain = true)
public class AttrAggV  implements Serializable {
	private Long aggSum;
	private Object value;
	private Object key;
}

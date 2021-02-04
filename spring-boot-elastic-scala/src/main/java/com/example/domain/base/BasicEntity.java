package com.example.domain.base;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 基本的实体
 *
 * @author shizeying
 * @date 2021/01/16
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
@Data
public abstract class BasicEntity {
	private String id;
	private String indexPrefix;
	
}

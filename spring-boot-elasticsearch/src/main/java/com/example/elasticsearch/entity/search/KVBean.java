package com.example.elasticsearch.entity.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * kvbean
 *
 * @author shizeying
 * @date 2021/01/02
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class KVBean<K,V> implements Serializable {
	
	private  K k;
	private  V v;
}

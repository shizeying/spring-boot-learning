package com.example.inter;

/**
 * 三个消费者
 *
 * @author shizeying
 * @date 2021/06/12
 */
@FunctionalInterface
public interface ThreeConsumer<K, V, S> {
	void accept(K var1, V var2, S var3);
	
}

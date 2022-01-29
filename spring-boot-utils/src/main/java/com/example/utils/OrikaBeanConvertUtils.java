package com.example.utils;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.*;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class OrikaBeanConvertUtils {
	private static final DefaultMapperFactory.Builder DEFAULTMAPPERFACTORYBUILDER = new DefaultMapperFactory.Builder();
	
	
	private static MapperFacade initNotNull() {
		return DEFAULTMAPPERFACTORYBUILDER
				
				.mapNulls(false).useAutoMapping(true)
				//属性生成策略
				// .propertyResolverStrategy()
				//class属性生成策略
				// .classMapBuilderFactory()
				.build()
				
				.getMapperFacade();
	}
	
	private static MapperFacade initNull() {
		return DEFAULTMAPPERFACTORYBUILDER
				
				//属性生成策略
				// .propertyResolverStrategy()
				//class属性生成策略
				// .classMapBuilderFactory()
				.build()
				
				.getMapperFacade();
	}
	
	
	/**
	 * 转换:空值自动删除
	 * tuple2._1:source
	 * tuple2._2:target
	 *
	 * @return {@code Consumer<Tuple2<T, R>>}
	 */
	public static <T, R> Consumer<Tuple2<T, R>> convertNotNull() {
		
		return tuple2 -> initNotNull().map(tuple2._1, tuple2._2);
	}
	
	/**
	 * 转换:空值自动删除
	 * tuple2._1:source
	 * tuple2._2:target
	 *
	 * @return {@code Consumer<Tuple2<T, R>>}
	 */
	public static <T, R> BiConsumer<T, R> convertNotNullBiConsumer() {
		
		return (source, target) -> initNotNull().map(source, target);
	}
	
	/**
	 * 转换:空值不会忽略
	 * tuple2._1:source
	 * tuple2._2:target
	 *
	 * @return {@code Consumer<Tuple2<T, R>>}
	 */
	public static <T, R> Consumer<Tuple2<T, R>> convertNull() {
		final DefaultMapperFactory.Builder builder = new DefaultMapperFactory.Builder();
		return tuple2 -> initNull().map(tuple2._1, tuple2._2);
	}
	
	/**
	 * 转换:空值自动删除
	 * tuple2._1:source
	 * tuple2._2:target
	 *
	 * @return {@code Consumer<Tuple2<T, R>>}
	 */
	public static <T, R> Function<T, R> convertNotNull(Class<R> clazz) {
		
		return source -> initNotNull().map(source, clazz);
	}
	
	/**
	 * 转换:空值不会忽略
	 * tuple2._1:source
	 * tuple2._2:target
	 *
	 * @return {@code Consumer<Tuple2<T, R>>}
	 */
	public static <T, R> Function<T, R> convertNull(Class<R> clazz) {
		return source -> initNull().map(source, clazz);
	}
	
	
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@ToString
	public static class A {
		
		private String a;
		private String b;
	}
	
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@ToString
	public static class B {
		
		private String a;
		private String b;
	}
	
	
	public static void main(String[] args) {
		final A a = A.builder().a("1").b("2").build();
		final B b = B.builder().a("3").build();
		
		
		OrikaBeanConvertUtils.convertNotNull().accept(Tuple.of(b, a));
		System.out.println(a);
		
		
	}
	
	
}

package com.example.utils;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ma.glasnost.orika.BoundMapperFacade;import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;import org.apache.poi.ss.formula.functions.T;

public class OrikaBeanConvertUtils {
	
	private static final DefaultMapperFactory.Builder DEFAULTMAPPERFACTORYBUILDER = new DefaultMapperFactory.Builder();
	
	
	private static <R,T> BoundMapperFacade<R,T> initNotNull(Class<R> rClass,Class<T> tClass) {
		return DEFAULTMAPPERFACTORYBUILDER
				
				.mapNulls(false).useAutoMapping(true)
				// 属性生成策略
				// .propertyResolverStrategy()
				// class 属性生成策略
				// .classMapBuilderFactory()
				.build()
				
				.getMapperFacade(rClass,tClass);
	}
	
	private static <R,T> BoundMapperFacade<R,T> initNull(Class<R> rClass,Class<T> tClass) {
		return DEFAULTMAPPERFACTORYBUILDER
				
				// 属性生成策略
				// .propertyResolverStrategy()
				// class 属性生成策略
				// .classMapBuilderFactory()
				.build()
				
				.getMapperFacade(rClass,tClass);
	}
	
	
	/**
	 * 转换: 空值自动删除 tuple2._1:source tuple2._2:target
	 *
	 * @return {@code Consumer<Tuple2<T, R>>}
	 */
	public static <T, R> Consumer<Tuple2<T, R>> convertNotNull() {
		
		return tuple2 -> initNotNull().map(tuple2._1, tuple2._2);
	}
	
	/**
	 * 转换: 空值自动删除 tuple2._1:source tuple2._2:target
	 *
	 * @return {@code Consumer<Tuple2<T, R>>}
	 */
	public static <T, R> BiConsumer<T, R> convertNotNullBiConsumer() {
		
		return (source, target) -> initNotNull((Class<T>) source.getClass(),target.getClass()).map(((T)source));
	}
	
	/**
	 * 转换: 空值不会忽略 tuple2._1:source tuple2._2:target
	 *
	 * @return {@code Consumer<Tuple2<T, R>>}
	 */
	public static <T, R> Consumer<Tuple2<T, R>> convertNull() {
		return tuple2 -> initNull((Class<T>) tuple2._1().getClass(),tuple2._2.getClass()).map(tuple2._1);
	}
	
	/**
	 * 转换: 空值自动删除 tuple2._1:source tuple2._2:target
	 *
	 * @return {@code Consumer<Tuple2<T, R>>}
	 */
	public static <T, R> Function<T, R> convertNotNull(Class<R> clazz) {
		
		return source -> initNotNull((Class<T>) source.getClass(),clazz).map(source);
	}
	
	/**
	 * 转换: 空值不会忽略 tuple2._1:source tuple2._2:target
	 *
	 * @return {@code Consumer<Tuple2<T, R>>}
	 */
	public static <T, R> Function<T, R> convertNull(Class<R> clazz) {
		return source -> initNull((Class<T>) source.getClass(),clazz).map(source);
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
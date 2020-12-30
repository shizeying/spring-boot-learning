package com.example.typehandle.utils;

import com.example.typehandle.annotation.ToMap;
import com.example.typehandle.entity.BasicEntity;
import com.google.common.collect.Maps;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 豆跑龙套
 *
 * @author shizeying
 * @date 2020/12/30
 */
@Component
public class BeanUtils<E extends BasicEntity> {
	/**
	 * 过滤不需要属性
	 *
	 * @param field
	 *
	 * @return
	 */
	private static Boolean needFilterField(Field field) {
		// 过滤静态属性
		if (Modifier.isStatic(field.getModifiers())) {
			return true;
		}
		// 过滤transient 关键字修饰的属性
		if (Modifier.isTransient(field.getModifiers())) {
			return true;
		}
		return false;
	}
	
	/**
	 * get父场 递归所有父类属性
	 *
	 * @param clazz
	 * 		clazz
	 * @param fields
	 * 		fields
	 */
	private static void getParentField(Class<?> clazz, List<Field> fields) {
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null) {
			Field[] superFields = superClazz.getDeclaredFields();
			for (Field field : superFields) {
				if (needFilterField(field)) {
					continue;
				}
				fields.add(field);
			}
			getParentField(superClazz, fields);
		}
	}
	
	/**
	 * java bean2地图
	 * javaBean转Map 使用自定义注解 运行时间大约为9毫秒 耗时短
	 *
	 * @param javaBean
	 * 		对象
	 * @param annotationClass
	 * 		自定义注解Class
	 *
	 * @return {@link Map<String, Object>}
	 *
	 * @throws IllegalAccessException
	 * 		非法访问异常
	 */
	public Map<String, Object> javaBean2Map(E javaBean, Class<? extends Annotation> annotationClass) throws IllegalAccessException {
		if (Objects.isNull(javaBean)) {
			return Maps.newHashMap();
		}
		//根据反射获得该javaBean下的所有字段
		Class<?> clazz = javaBean.getClass();
		List<Field> fields = Stream
				                     .of(clazz.getDeclaredFields())
				                     .collect(Collectors.toList());
		getParentField(clazz, fields);
		return io.vavr.collection
				       .List
				       .of(fields)
				       .flatMap(i -> i)
				       .distinct()
				       .filter(field -> field.isAnnotationPresent(annotationClass))
				       .map(field -> toMap(field, javaBean, annotationClass))
				       .flatMap(i -> i)
				       .filter(tuple -> Objects.nonNull(tuple._2))
				       .distinct()
				       .toJavaMap(HashMap::new, Tuple2::_1, Tuple2::_2);
	}
	
	private io.vavr.collection.List<Tuple2<String, Object>> toMap(Field field, E javaBean, Class<? extends Annotation> clazz) {
		PropertyDescriptor pd = Try
				                        .of(() -> new PropertyDescriptor(field.getName(), javaBean.getClass()))
				                        .onFailure(Throwable::printStackTrace)
				                        .get();
		
		return Try
				       .of(() -> field.getAnnotation(clazz))
				       .onFailure(Throwable::printStackTrace)
				       .filter(ToMap.class::isInstance)
				       .filter(Objects::nonNull)
				       .map(annotation -> Tuple.of(
						       ((ToMap) annotation).key(),
						       Try
								       .of(()
										           -> pd.getReadMethod()
										                .invoke(javaBean)).get()))
				       .toList();
		
		
	}
	
}

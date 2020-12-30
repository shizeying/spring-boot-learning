package com.example.typehandle.annotation;

import java.lang.annotation.*;

/**
 * 映射
 *
 * @author shizeying
 * @date 2020/12/30
 */
@Target(ElementType.FIELD) //表示作用的字段上
@Retention(RetentionPolicy.RUNTIME) //表示生效时间:运行时
@Documented //表示只负责标记,不负责取值
public @interface ToMap {
	
	public String key() default "";
}

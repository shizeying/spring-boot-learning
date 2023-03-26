package com.example.utils.bean;

import io.vavr.control.Try;
import java.util.function.Consumer;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.Test;

public class BeanUtilsTest {
	private final  static MapperFactory DEFAULTMAPPERFACTORYBUILDER = new DefaultMapperFactory.Builder().build();
	 @Test
	 void testBeanUtilsTest() throws Exception {
		 final TestB build = TestB.builder().code(123).build();
		 final TestA build1 = TestA.builder().test(build).build();
		 final TestC map =
								 DEFAULTMAPPERFACTORYBUILDER.getMapperFacade(TestA.class,TestC.class).map(null);
		 System.out.println(map.getTest().getCode());
		
		
	 }
	 
	 	public static <R,T> T obj2Obj(final R srcObj, Class<T> tgtClass, Consumer<T> consumer) {
				T tgt = Try.of(tgtClass::newInstance).getOrNull();
				if (srcObj == null) {
						return tgt;
				}
		  final T map = DEFAULTMAPPERFACTORYBUILDER.getMapperFacade().map(null, tgtClass);
		  return tgt;
		}

}
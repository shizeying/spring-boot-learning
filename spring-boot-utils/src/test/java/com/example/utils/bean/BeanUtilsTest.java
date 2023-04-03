package com.example.utils.bean;

import io.vavr.control.Try;
import java.util.function.Consumer;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.Test;import org.modelmapper.ModelMapper;

public class BeanUtilsTest {
	private final  static MapperFactory DEFAULTMAPPERFACTORYBUILDER = new DefaultMapperFactory.Builder().build();
		
		@Test
		void testBeanUtilsTest() throws Exception {
				final TestB build       = TestB.builder().code(123).build();
				final TestA build1      = TestA.builder().test(build).build();
				ModelMapper modelMapper = new ModelMapper();
				TestC       map         = modelMapper.map(build1, TestC.class);
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
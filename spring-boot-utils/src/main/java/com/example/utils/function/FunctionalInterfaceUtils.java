package com.example.utils.function;


import com.example.utils.config.MyErrorException2;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Slf4j
public class FunctionalInterfaceUtils {
	
	static <R, T, E extends Throwable> R rethrow(MyErrorException2 ex, T t) throws E {
		// ex.setClass(t.getClass());
		// throw  (E) ex;
		System.out.println(ex.getMessage());
		return null;
	}
	
	public static <R, E extends Exception> Stream<R> of(Stream<R> stream) throws E {
		return stream;
	}
	
	public static <R, E1 extends Exception, E2 extends Exception> Stream<R> of2(Stream<R> stream) throws E1, E2 {
		return stream;
	}
	
	public static <R, E1 extends Exception, E2 extends Exception, E3 extends Exception> Stream<R> of3(Stream<R> stream) throws E1, E2, E3 {
		return stream;
	}
	
	public static <R, E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception> Stream<R> of4(Stream<R> stream) throws E1, E2, E3, E4 {
		return stream;
	}
	
	public static <R, T> R wrap(ToMapFun<R, T> func, T t) {
		
		try {
			return func.apply(t);
		} catch (Throwable ex) {
			return rethrow(new MyErrorException2(ex), t);
		}
	}
	
	@FunctionalInterface
	public interface ToMapFun<R, T> {
		R apply(T t) throws Throwable;
	}
	
}

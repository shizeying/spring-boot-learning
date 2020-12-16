package com.example.utils;

import io.vavr.*;
import io.vavr.collection.Queue;
import io.vavr.control.Option;
import io.vavr.test.Arbitrary;
import io.vavr.test.Property;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class vavrTest {
	
	
	Function1<Integer, Integer> plusTwo = a -> a + 2;
	Function1<Integer, Integer> multiplyByTwo = a -> a * 2;
	Function1<Integer, Integer> minusTwo = a -> a - 2;
	
	@Test
	public void setOptional() {
		Optional<String> maybeFoo = Optional.of("foo");
		then(maybeFoo.get()).isEqualTo("foo");
		Optional<String> maybeFooBar = maybeFoo.map(s -> (String) null)
				.map(s -> s.toUpperCase() + "bar");
		System.out.println(maybeFooBar.isPresent());
		then(maybeFooBar.isPresent()).isFalse();
	}
	@Test
	public  void  setQueue(){
		Queue<Integer> queue = Queue.of(1, 3, 2)
				.enqueue(5)
				.enqueue(4);
		System.out.println(queue.get());
	}
	
	@Test
	public void setStream() {
		Tuple2<String, Integer> java8 = Tuple.of("Java", 8);
		System.out.println(java8._2());
		Function1<Integer, Integer> plusOne = a -> a + 1;
		Function1<Integer, Integer> multiplyByTwo = a -> a * 2;
		
		Function1<Integer, Integer> add1AndMultiplyBy2 = plusOne
				.andThen(multiplyByTwo)
				
				
				;
		
		then(add1AndMultiplyBy2.apply(2)).isEqualTo(6);
		Arbitrary<Integer> ints = Arbitrary.integer();

// square(int) >= 0: OK, passed 1000 tests.
		Property.def("square(int) >= 0")
				.forAll(ints)
				.suchThat(i -> i * i >= 0)
				.check()
				.assertIsSatisfied();
	}
	
	/**
	 * @author: <a href="MAILTO: w741069229@gmail.com">shizeying</a>
	 * @description: {@link Function1 andThen }  测试接口
	 * @return:
	 * @time: 2020/11/08
	 */
	@Test
	void andThenComposition() {
		Function1<Integer, Integer> add1MultiplyBy2AndSubtract2
				= plusTwo.andThen(multiplyByTwo).andThen(minusTwo);
		
		System.out.println((add1MultiplyBy2AndSubtract2.apply(3)));
		assertThat(add1MultiplyBy2AndSubtract2.apply(3).intValue())
				.isEqualTo(8);
	}
	
	/**
	 * @author: <a href="MAILTO: w741069229@gmail.com">shizeying</a>
	 * @description: {@link Function1 compose }  测试接口
	 * @return:
	 * @time: 2020/11/08
	 */
	@Test
	void composeComposition() {
		Function1<Integer, Integer> add1MultiplyBy2AndSubtract2
				= minusTwo.compose(multiplyByTwo).compose(plusTwo);
		System.out.println(add1MultiplyBy2AndSubtract2.apply(3));
		
		assertThat(add1MultiplyBy2AndSubtract2.apply(3).intValue())
				.isEqualTo(8);
	}
	@Test
	void setFlatMap(){
		List<TestEntity> testEntities=new ArrayList<>();

	for (Integer i=0;i<100;i++){
		TestEntity build = TestEntity
				.builder()
				.id(i.longValue())
				.name("张三" + i)
				.count((i.longValue() * 2))
				.build();

		testEntities.add(build);

	}
		io.vavr.collection.List<Tuple2<Long, io.vavr.collection.List<TestEntity>>> tuple2s = io.vavr.collection.List
				                                                                  .ofAll(testEntities)
				                                                                  .groupBy(TestEntity::getId)
		
				                                                                  .toList();
		io.vavr.collection.List<TestEntity> entities = tuple2s
				                                 .map(Tuple2::_2)
				                                 .flatMap(t -> t)
				                                 .toList();
		io.vavr.collection.List<io.vavr.collection.List<TestEntity>> test= io.vavr.collection.List.of(entities);
		List<TestEntity> testEntities1 = test
				                                 .flatMap(t -> t)
				                                 .toJavaList();
		
		
	}
	
	/**
	 * @author: <a href="MAILTO: w741069229@gmail.com">shizeying</a>
	 * @description: 提升 函数运算无状态返回，若正确  {@link  Option<Integer>.isDefined}    返回true
	 * @return:
	 * @time: 2020/11/08
	 */
	@Test
	void lifting() {
		Function2<Integer, Integer, Integer> divide = (x, y) -> x / y;
		
		//ArithmeticException will be thrown
		//Integer result = divide.apply(5, 0);                                                                        
		
		Function2<Integer, Integer, Option<Integer>> safeDivide
				= Function2
						  .lift(divide);
		
		Option<Integer> result = safeDivide.apply(10, 0);
		Option<Integer> noResult = safeDivide.apply(10, 0);
		Function2<Integer, Integer, Option<Integer>> sum = Function2.lift(this::sum);
		Option<Integer> optionalResult = sum.apply(-1, 2);
		System.out.println(optionalResult.getOrNull());
		
		System.out.println(result.isDefined());
		assertTrue(result.isEmpty());
		assertTrue(noResult.isEmpty());
	}
	
	private int sum(int first, int second) {
		if (first < 0 || second < 0) {
			throw new IllegalArgumentException("Only positive integers are allowed");
		}
		return first + second;
	}
	
	/**
	 * @author: <a href="MAILTO: w741069229@gmail.com">shizeying</a>
	 * @description: 记忆化是一种缓存形式。 memoized函数只执行一次，然后从缓存中返回结果
	 * @return:
	 * @time: 2020/11/08
	 */
	@Test
	void memoization() {
		Function0<Double> getRandom = Math::random;
		Function0<Double> getCachedRandom = getRandom.memoized();
		
		assertThat(getRandom.apply())
				.isNotEqualTo(getRandom.apply());
		
		System.out.println(getCachedRandom.apply());
		assertThat(getCachedRandom.apply())
				.isEqualTo(getCachedRandom.apply());
	}
	
	/**
	 * @author: <a href="MAILTO: w741069229@gmail.com">shizeying</a>
	 * @description: 可继承
	 * @return:
	 * @time: 2020/11/08
	 */
	@Test
	void partialApplication() {
		Function4<Integer, Integer, Integer, Integer, Integer> sum
				= (a, b, c, d) -> a + b + c + d;
		Function2<Integer, Integer, Integer> sum12 = sum.apply(1, 2);
		
		System.out.println(sum12.curried().apply(1).apply(1));
		assertThat(sum.apply(1, 1, 1, 1)).isEqualTo(4);
		assertThat(sum12.apply(3, 4)).isEqualTo(10);
	}
	
	@Test
	public void setCurrying() {
		Function2<Integer, Long, Integer> sum = (a, b) -> {
			System.out.println(a + "+" + b);
			return a + b.intValue();
			
		};
		//TODO b => sum.curried()-><a,Function1<Long,Integer>>
		//TODO Function1<Integer, Function1<Long, Integer>> curried = sum.curried();
		Function1<Long, Integer> add2 = sum.curried().apply(1);
		Function1<Integer, Function1<Long, Integer>> curried = sum.curried();
		System.out.println(add2.apply(4L));
		then(add2.apply(4L)).isEqualTo(6);
	}
	
}





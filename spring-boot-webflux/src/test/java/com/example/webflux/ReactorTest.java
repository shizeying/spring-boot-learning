package com.example.webflux;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

public class ReactorTest {
	
	
	/**
	 * 订阅
	 */
	@Test
	public void setSubscribe(){
		//TODO 配置一个在订阅时会产生3个值的 Flux
		 Flux<Integer> ints = Flux.range(1, 3);
		 //TODO 订阅它并打印值。
		 ints.subscribe(System.out::println);
		 //TODO 引入一个错误
		Flux<Integer> intsError = Flux.range(1, 4)
				.map(i -> {
					if (i <= 3) return i;
					throw new RuntimeException("Got to 4");
				});
		intsError.subscribe(System.out::println, error -> System.err.println("Error: " + error));
		//TODO 既有错误处理，还有一个完成后的处理
		Flux<Integer> intsErrorDone = Flux.range(1, 4);
		intsErrorDone.subscribe(System.out::println,
				error -> System.err.println("Error " + error),
				() -> {System.out.println("Done");});
	 }
	 @Test
	 public void  setSampleSubscriber() throws InterruptedException {
		 SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
		 Flux<Integer> ints = Flux.range(1, 4);
		 ints.subscribe(System.out::println,
				 error -> System.err.println("Error " + error),
				 () -> {System.out.println("Done");},
				 s -> ss.hookOnNext(10));
		 ints.subscribe(ss);
	 }
	 
	public class SampleSubscriber<T> extends BaseSubscriber<T> {
		
		public void hookOnSubscribe(Subscription subscription) {
			System.out.println("Subscribed1");
			//request(100);
			System.out.println("Subscribed2");
		}
		
		public void hookOnNext(T value) {
			System.out.println("value=="+value);
			request(1);
		}
	}
	
	
	/**
	 * 基于状态值的 generate 示例 不可变
	 */
	@Test
	public void setGenerateImmutable(){
		Flux<String> flux = Flux.generate(
				() -> 0,
				(state, sink) -> {
					sink.next("3 x " + state + " = " + 3*state);
					if (state == 10) sink.complete();
					return state + 1;
				});
		flux.subscribe(System.out::println);
	}
	
	
	/**
	 * 基于状态值的 generate 示例 可变
	 */
	@Test
	public void setGenerateMutable(){
		Flux<String> flux = Flux.generate(
				AtomicLong::new,
				(state, sink) -> {
					long i = state.getAndIncrement();
					sink.next("3 x " + i + " = " + 3*i);
					if (i == 10) sink.complete();
					return state;
				});
		flux.subscribe(System.out::println);
	}
	@Test
	public void setGenerateConsumer(){
		Flux<String> flux = Flux.generate(
				AtomicLong::new,
				(state, sink) -> {
					long i = state.getAndIncrement();
					sink.next("3 x " + i + " = " + 3*i);
					if (i == 10) sink.complete();
					return state;
				}, (state) -> System.out.println("state: " + state));
		flux.subscribe(System.out::println);
	}
	@Test
	public void setTransform(){
		Function<Flux<String>, Flux<String>> filterAndMap =
				f -> f.filter(color -> !color.equals("orange"))
						.map(String::toUpperCase);
		
		Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
				.doOnNext(System.out::println)
				.transform(filterAndMap)
				.subscribe(d -> System.out.println("Subscriber to Transformed MapAndFilter: "+d));
	}
	@Test
	public void setCompose(){
		AtomicInteger ai = new AtomicInteger();
		Function<Flux<String>, Flux<String>> filterAndMap = f -> {
			if (ai.incrementAndGet() == 1) {
				return f.filter(color -> !color.equals("orange"))
						.map(String::toUpperCase);
			}
			return f.filter(color -> !color.equals("purple"))
					.map(String::toUpperCase);
		};
		
		Flux<String> composedFlux =
				Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
						.doOnNext(System.out::println)
						.compose(filterAndMap);
		
		composedFlux.subscribe(d -> System.out.println("Subscriber 1 to Composed MapAndFilter :"+d));
		composedFlux.subscribe(d -> System.out.println("Subscriber 2 to Composed MapAndFilter: "+d));
	}
	
	@Test
	public void setProcessor(){
		Flux<String> source = Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
				.doOnNext(System.out::println)
				.filter(s -> s.startsWith("o"))
				.map(String::toUpperCase);
		
		source.subscribe(d -> System.out.println("Subscriber 1: "+d));
		source.subscribe(d -> System.out.println("Subscriber 2: "+d));
		System.err.println("###################");
		UnicastProcessor<String> hotSource = UnicastProcessor.create();
		
		Flux<String> hotFlux = hotSource.publish()
				.autoConnect()
				.map(String::toUpperCase);
		
		
		hotFlux.subscribe(d -> System.out.println("Subscriber 1 to Hot Source: "+d));
		
		hotSource.onNext("blue");
		hotSource.onNext("green");
		
		hotFlux.subscribe(d -> System.out.println("Subscriber 2 to Hot Source: "+d));
		
		hotSource.onNext("orange");
		hotSource.onNext("purple");
		hotSource.onComplete();
	}
	@Test
	public void setConnectableFlux() throws InterruptedException {
		Flux<Integer> source = Flux.range(1, 3)
				.doOnSubscribe(s -> System.out.println("subscribed to source"));
		
		ConnectableFlux<Integer> co = source.publish();
		
		co.subscribe(System.out::println, e -> {}, () -> {});
		co.subscribe(System.out::println, e -> {}, () -> {});
		
		System.out.println("done subscribing");
		Thread.sleep(500);
		System.out.println("will now connect");
		
		co.connect();
	}
	
	
}


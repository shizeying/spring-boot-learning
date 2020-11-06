package com.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import io.vavr.Function2;
import io.vavr.Function4;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootUtilsApplicationTests {

	@Test
	void contextLoads() {
	}
	public int removeDuplicates(int[] nums) {

		
		Long count = Stream.of(nums).distinct().count();
		return count.intValue();
		
	}
  //  public static void main(String[] args) {
  //		//
  //		Stopwatch stopwatch = Stopwatch.createStarted();
  //// do something
  //		long second = stopwatch.elapsed(TimeUnit.SECONDS);
  //    System.out.println(second);
  //  }
	private static ThreadLocalRandom random =
			ThreadLocalRandom.current();
  public static void main(String[] args) {
		Function4< Integer, Integer, Integer, Integer, Integer> function4 =
				(v1, v2, v3, v4) -> (v1 + v2) * (v3 + v4);
		Function2< Integer, Integer, Integer> function2 = function4.apply(1, 2);
		int result1 = function2.apply(4, 5);
		System.out.println(result1);
		Option<String> str = Option.of("Hello");
		str.map(String::length);
		Integer integer = str.flatMap(v -> Option.of(v.length())).get();
		Try<Integer> result = Try.of(() -> 1 / 0).recover(e -> 1);
		System.out.println(result);
	
		Try<String> lines = Try.of(() -> Files.readAllLines(Paths.get("1.txt")))
				.map(list -> String.join(",", list))
				.andThen(() ->System.out.println("test"))
				
				;
		System.out.println(lines);
	}
}

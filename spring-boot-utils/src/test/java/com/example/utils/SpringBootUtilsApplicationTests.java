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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
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
		Function<Integer, Integer> times2 = i -> i+1;
		Function<Integer, Integer> squared = i -> i*i;
		
		//System.out.println(times2.apply(4));
		//System.out.println(squared.apply(4));
		
		//System.out.println(times2.compose(squared).apply(3));  //32                先4×4然后16×2,
		// 先执行apply(4)，在times2的apply(16),先执行参数，再执行调用者。
		System.out.println(times2.andThen(squared).apply(4));  //64               先4×2,然后8×8,先执行times2的函数，在执行squared的函数。
		
		System.out.println(Function.identity().compose(squared).apply(4));   //16
	}
}

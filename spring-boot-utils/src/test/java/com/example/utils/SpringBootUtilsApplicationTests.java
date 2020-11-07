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
		List<String> strings=new ArrayList<>();
		strings.add("1");
		strings.add("2");
		strings.add("3");
		strings.add("4");
		strings.add("5");
		strings.add("6");
		String[] strings1 = {"1", "2", "90"};
		List<String> strings2 = Arrays.stream(strings1).collect(Collectors.toList());
		strings.removeAll(strings2);
		System.out.println(strings);
	}
}

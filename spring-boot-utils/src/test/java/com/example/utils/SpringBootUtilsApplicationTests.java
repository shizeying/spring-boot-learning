package com.example.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import io.vavr.Function1;
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
import org.assertj.core.api.Assertions;
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
		Function1<String, String> toUpper = String::toUpperCase;
		Function1<String, String> trim = String::trim;
		Function1<String, String> cheers = (s) -> String.format("Hello %s", s);
		assertThat(trim
				.andThen(toUpper)
				.andThen(cheers)
				.apply("   john")).isEqualTo("Hello JOHN");
		Function1<String, String> composedCheer =
				cheers.compose(trim).compose(toUpper);
		System.out.println(trim
				.andThen(toUpper)
				.andThen(cheers)
				.apply("   john"));
		assertThat(composedCheer.apply(" steve ")).isEqualTo("Hello STEVE");
	}
}

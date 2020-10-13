package com.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
  public static void main(String[] args) {
		
  }
}

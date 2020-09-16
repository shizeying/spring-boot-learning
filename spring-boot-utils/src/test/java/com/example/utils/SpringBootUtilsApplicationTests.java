package com.example.utils;

import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
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
  public static void main(String[] args) {
		//
		Stopwatch stopwatch = Stopwatch.createStarted();
// do something
		long second = stopwatch.elapsed(TimeUnit.SECONDS);
    System.out.println(second);
  }
}

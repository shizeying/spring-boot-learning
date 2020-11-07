package com.example.utils.config;

import io.vavr.control.Try;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {
	
	public static String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		return Try.of(() -> {
			throwable.printStackTrace(pw);
			return sw.toString();
		})
				.andFinally(() -> pw.close()).get();
	}
}


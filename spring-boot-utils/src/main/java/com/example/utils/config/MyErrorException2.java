package com.example.utils.config;

import java.io.PrintStream;
import java.io.PrintWriter;

public class MyErrorException2 extends Throwable {
	private Class clazz;
	
	public MyErrorException2(final Throwable cause) {
		super(cause);
	}
	
	@Override
	public void printStackTrace() {
		System.out.println(2);
		// super.printStackTrace();
	}
	
	@Override
	public void printStackTrace(final PrintStream s) {
		System.out.println(3);
		// super.printStackTrace(s);
	}
	
	@Override
	public void printStackTrace(final PrintWriter s) {
		System.out.println(1);
		// super.printStackTrace(s);
	}
	
	@Override
	public synchronized Throwable fillInStackTrace() {
		// return super.fillInStackTrace();
		return null;
	}
	
	
}

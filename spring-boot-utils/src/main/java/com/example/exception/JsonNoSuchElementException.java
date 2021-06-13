package com.example.exception;

import java.util.*;

/**
 * json转换异常
 *
 * @author shizeying
 * @date 2021/06/12
 */
public class JsonNoSuchElementException extends NoSuchElementException {
	public JsonNoSuchElementException() {
		super("json读取异常，不是正确的json");
	}
}

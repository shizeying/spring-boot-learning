package com.example.webflux.controller;

import com.example.webflux.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author <a href="MAILTO: w741069229@gmail.com">shizeying</a>
 * @version 1.0.0
 * @des {@link HelloWebFluxController} hello,world
 * @date 2020/11/18
 * @since 1.0.0
 */
@RestController
public class HelloWebFluxController {
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello, WebFlux !";
	}
	
	@GetMapping("/user")
	public Mono<User> getUser() {
		User user = new User();
		user.setName("犬小哈");
		user.setDesc("欢迎关注我的公众号: 小哈学Java");
		return Mono.just(user);
	}
	
	
}


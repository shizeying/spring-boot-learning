package com.example.webflux;

import com.example.utils.config.JacksonUtil;
import com.example.webflux.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

//@SpringBootTest
class WebfluxApplicationTests {
	
	
	@Test
	public void setResult() {
		
		List<User> userList = new ArrayList<>();
		userList.add(User.builder().id(1L).desc("dafds").name("abc").build());
		userList.add(User.builder().id(1L).desc("dafds1").name("abc1").build());
		userList.add(User.builder().id(2L).desc("dafds2").name("abc2").build());
		userList.add(User.builder().id(2L).desc("dafds3").name("abc3").build());
		userList.add(User.builder().id(3L).desc("dafds4").name("abc4").build());
		userList.add(User.builder().id(3L).desc("dafds5").name("abc5").build());
		userList.add(User.builder().id(3L).desc("dafds6").name("abc6").build());
		userList.add(User.builder().id(4L).desc("dafds4").name("abc7").build());
		userList.add(User.builder().id(5L).desc("dafds6").name("abc8").build());
		
		io.vavr.collection.List<User> users = io.vavr.collection.List.ofAll(userList);
		
		Function<User, Map<String, User>> mapFunction = user -> {
			Map<String, User> map = new ConcurrentHashMap<>();
			map.put(user.getName(), user);
			return map;
		};
		Function<io.vavr.collection.List<User>, List<Map<String, User>>> usersGroups =
				usersGroup ->
						usersGroup
								.map(mapFunction)
								.toJavaList();
		Map<Long, List<Map<String, User>>> map = users
				.groupBy(User::getId)
				.mapValues(usersGroups)
				.toJavaMap();
		System.out.println(JacksonUtil.bean2Json(map));
		
	}
	
	
	@Test
	void contextLoads() {
	}
	
	
}
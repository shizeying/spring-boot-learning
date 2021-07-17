package com.example.easyrule.service;

import com.ilxqx.rules.starter.support.*;
import lombok.*;
import org.springframework.core.annotation.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class DemoService {
	// 引入 easyRulesTemplate
	private final EasyRulesTemplate easyRulesTemplate;
	
	public void executeRule(Order order) {
		// 触发指定的规则组
		this.easyRulesTemplate.fire("orderValidation", order);
	}
}


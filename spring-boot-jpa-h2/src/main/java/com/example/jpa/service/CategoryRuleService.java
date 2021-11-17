package com.example.jpa.service;

import com.example.jpa.constant.PullDownEnum;
import com.example.jpa.entity.CategoryRuleEntity;
import com.example.jpa.model.dto.CategoryRuleDto;
import com.example.jpa.model.qo.CategoryRuleSearchQo;
import com.example.jpa.model.vo.CategoryRuleAddVo;
import java.util.List;
import org.springframework.data.domain.Page;

public interface CategoryRuleService {
	
	CategoryRuleDto add(CategoryRuleAddVo data);
	
	Page<CategoryRuleEntity> page(CategoryRuleSearchQo data);
	
	void deleteById(Long id);
	
	/**
	 * 下拉 从图谱中获取
	 *
	 * @param type 类型
	 * @return {@code List<String>}
	 */
	List<String> pullDown(PullDownEnum type);
}

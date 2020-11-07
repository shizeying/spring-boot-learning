package com.example.mybatis.plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.plus.entity.SearchSnapshotQo;
import com.example.mybatis.plus.entity.SnapshotGroup;
import com.example.mybatis.plus.mapper.GraphConfSnapshotMapper;
import com.example.mybatis.plus.service.SnapshotService;
import com.example.utils.config.JacksonUtil;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisPlusApplicationTests {
	 @Autowired
	 private SnapshotService service;
	 @Autowired
	 private GraphConfSnapshotMapper mapper;
	@Test
	void contextLoads() {
	}
	@Test
	public  void  setService(){
		SnapshotGroup group = new SnapshotGroup()
				.setFocusEntityName("dfa")
				.setSubjectName("test")
				.setGraphConfSnapshotId(1L);
		//Assertions.assertTrue(service.saveOrUpdateBatch(Arrays.asList(group)));
		//Assertions.assertTrue(service.deleteSnapshot(Arrays.asList(100L)));
		//IPage<Map<String, Object>> page = service.groupBySubjectName(new SearchSnapshotQo());
		//List<Map<String, Object>> records = page.getRecords();
		//System.out.println(records);
		Page<Map<String, Object>> page =  new Page<Map<String, Object>>();
		QueryWrapper queryWrapper = new QueryWrapper<>();
		//System.out.println(JacksonUtil.bean2Json(mapper.groupBySubjectName(page, queryWrapper)));
		System.out.println(JacksonUtil.bean2Json(service.search(new SearchSnapshotQo()).getRecords()));
		//System.out.println(mapper.groupBySubjectName2(page, queryWrapper));
	}

}

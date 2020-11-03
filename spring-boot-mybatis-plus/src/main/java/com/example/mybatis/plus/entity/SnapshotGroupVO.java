package com.example.mybatis.plus.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@ApiModel
@Accessors(chain = true)
@SuppressWarnings("ALL")
public class SnapshotGroupVO {
	
	private Page<List<Map<String, Object>>> grouping;
	private IPage<GraphConfSnapshotGroupBO> entities;
}

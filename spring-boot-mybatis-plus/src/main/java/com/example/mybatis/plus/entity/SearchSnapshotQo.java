package com.example.mybatis.plus.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@ApiModel
@SuppressWarnings("ALL")
@Accessors(chain = true)
public class SearchSnapshotQo {
	
	@ApiModelProperty("分组收藏夹名称")
	private String subjectName;
	@ApiModelProperty("焦点实体")
	private String focusEntityName;
	@ApiModelProperty("快照名称")
	private String name;
	private String spaId;
	private Page page;
	
	
}

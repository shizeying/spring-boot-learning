package com.example.jpa.model.qo;

import com.example.jpa.constant.SortEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Api(tags = "分类规则搜索交互对象", value = "分类规则搜索交互对象")
public class CategoryRuleSearchQo {
	
	/**
	 * 关键字
	 */
	@ApiModelProperty(value = "关键字", dataType = "string", example = "关键字")
	private String kw;
	/**
	 * 类别名称
	 */
	@ApiModelProperty(value = "类别名称", dataType = "string",example = "类别名称")
	private String typeName;
	/**
	 * 分类代码
	 */
	@ApiModelProperty(value = "分类代码", dataType = "string",example = "分类代码")
	private String categoryCode;
	/**
	 * 生产厂家
	 */
	@ApiModelProperty(value = "生产厂家", required = true, dataType = "string", example = "生产厂家")
	private String productionFactory;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@ApiModelProperty(value = "开始时间", dataType = "Date")
	private Date startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@ApiModelProperty(value = "结束时间", dataType = "Date")
	private Date endDate;
	@ApiModelProperty(value = "排序方式", dataType = "SortEnum",example = "asc")
	private SortEnum sort = SortEnum.asc;
	@ApiModelProperty(value = "page", dataType = "Integer",example = "1")
	@Builder.Default
	private Integer page = 1;
	@ApiModelProperty(value = "pageSize", dataType = "Integer",example = "10")
	@Builder.Default
	private Integer pageSize = 10;
	
	
}

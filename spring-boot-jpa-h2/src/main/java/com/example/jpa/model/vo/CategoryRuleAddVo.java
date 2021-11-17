package com.example.jpa.model.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 分类规则添加交互对象
 *
 * @author shizeying
 * @date 2021/11/16
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Api(tags = "分类规则添加交互对象", value= "分类规则添加交互对象")
public class CategoryRuleAddVo {
	@ApiModelProperty(value = "主键id，存在就为更新", required = false, dataType = "string", example = "null")
	private Long id;
	
	/**
	 * 产品名称
	 */
	@ApiModelProperty(value = "产品名称", required = true, dataType = "string", example = "")
	private String productName;
	/**
	 * 产品型号
	 */
	@ApiModelProperty(value = "产品型号", required = true, dataType = "string", example = "")
	private String productModel;
	/**
	 * 生产厂家
	 */
	@ApiModelProperty(value = "生产厂家", required = false, dataType = "string", example = "生产厂家")
	private String productionFactory;
	
	/**
	 * 分类代码
	 */
	@ApiModelProperty(value = "分类代码", required = false, dataType = "string", example = "分类代码")
	private String categoryCode;
	/**
	 * 分类代码
	 */
	@ApiModelProperty(value = "类别名称", required = false, dataType = "string", example = "类别名称")
	private String typeName;
	
	
}

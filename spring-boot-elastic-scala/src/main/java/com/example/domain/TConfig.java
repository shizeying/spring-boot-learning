package com.example.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.domain.bo.Attr;
import com.example.domain.bo.GeneralBuild;
import com.example.handle.AttrsHandler;
import com.example.handle.GeneralBuildsHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import com.example.domain.base.BaseEntity;
/**
 * 配置表：保存搜索组件初始化配置
 */
@ApiModel(value = "ai-plantdata-kgcloud-dimain-TConfig")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_config", autoResultMap = true)
public class TConfig extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * ES索引名,多索引模式会失效
	 */
	@TableField(value = "index_name")
	@ApiModelProperty(value = "ES索引名,多索引模式会失效")
	private String indexName;
	/**
	 * ES类型
	 */
	@TableField(value = "mapping")
	@ApiModelProperty(value = "ES类型")
	private String mapping;
	/**
	 * 搜索组件初始化配置
	 */
	@TableField(value = "config", typeHandler = GeneralBuildsHandler.class)
	@ApiModelProperty(value = "搜索组件初始化配置")
	private List<GeneralBuild> config;
	/**
	 * 搜索库名
	 */
	@TableField(value = "chinese_name")
	@ApiModelProperty(value = "搜索库名")
	private String chineseName;
	/**
	 * 向量任务id
	 */
	@TableField(value = "task_id")
	@ApiModelProperty(value = "向量任务id")
	private Integer taskId;
	/**
	 * 索引前缀集合和对应中文名称集合，
	 * 后缀统一为_yyyymmddhhmm,
	 * 多个索引用｜来进行分割，
	 * 中文名称和索引前缀使用,分割
	 * 格式为
	 * index_,测试｜relation_,关系
	 */
	@TableField(value = "indexes_list", typeHandler = AttrsHandler.class)
	@ApiModelProperty(value = "索引前缀集合和对应中文名称集合，,后缀统一为_yyyymmddhhmm,,多个索引用｜来进行分割，,中文名称和索引前缀使用,分割,格式为,index_,测试｜relation_,关系")
	private List<Attr> indexesList;
	
	@Builder
	public TConfig(Long id, LocalDateTime createAt, LocalDateTime updateAt, String indexName, String mapping,
	               List<GeneralBuild> config, String chineseName, Integer taskId, List<Attr> indexesList) {
		super(id, createAt, updateAt);
		this.indexName = indexName;
		this.mapping = mapping;
		this.config = config;
		this.chineseName = chineseName;
		this.taskId = taskId;
		this.indexesList = indexesList;
	}
}
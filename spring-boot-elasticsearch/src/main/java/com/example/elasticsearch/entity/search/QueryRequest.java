package com.example.elasticsearch.entity.search;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel(value = "搜索参数")
public class QueryRequest {
	
	/**
	 * 搜索词
	 */
	@ApiParam(value = "搜索词")
	private String kw;
	/**
	 * kw搜索时是否分词,0:不分 1:分词
	 */
	@ApiParam("kw搜索时是否分词,0:不分 1:分词")
	private String isSegment;
	/**
	 * 本次搜索唯一标识
	 */
	@ApiParam("本次搜索唯一标识")
	private String id;
	/**
	 * 排序规则,0升序，1降序
	 */
	@ApiParam("排序规则,0升序，1降序")
	private String sorts;
	/**
	 * 排序字段
	 */
	@ApiParam("排序字段")
	private String sortField;
	@ApiParam(value = "请求时间戳")
	/**
	 * 请求时间戳
	 */
	private Long tt;
	/**
	 * 页码 默认
	 */
	@ApiParam(value = "页码 默认")
	private Integer pageNo = 1;
	/**
	 * 页数 默认20
	 */
	@ApiParam(value = "页数 默认20")
	private Integer pageSize = 20;
	/**
	 * 聚合分组个数
	 */
	@ApiParam(value = "聚合分组个数")
	private Integer aggregationSize = 10;
	/**
	 * 是否开启explain，默认false
	 */
	@ApiParam(value = "是否开启explain，默认false")
	private Boolean isExplain = false;
	/**
	 * 查询index，全部为[all]
	 */
	@ApiParam("查询index，全部为[all]")
	private List<String> docTypeList;
	/**
	 * 直接聚合过滤条件
	 */
	@ApiParam("直接聚合过滤条件")
	private Integer isFilter = 0;
	/**
	 * 高级查询条件
	 */
	@ApiParam("高级查询条件")
	private List<HeightQueryBean> heightQueryBean;
	/**
	 *  修改为[{k: 'earliest_publication_date', v: {'lt':['2017']}, d: '发表年份'}]
	 */
	@ApiParam("过滤条件，比如 [{k: 'earliest_publication_date', v: ['2017'], d: '发表年份'}]")
	private List<KVBean<String, Object>> filters = new ArrayList<>();
	/**
	 * 自定义Includ聚合条件
	 */

	@ApiParam("自定义Includ聚合条件")
	private List<String> includeExclude;
	/**
	 * 使用自定义Term聚合还是配置聚合,有参数则是配置聚合
	 */
	@ApiParam("使用自定义Term聚合还是配置聚合,有参数则是配置聚合")
	private String isAggs;
	/**
	 *   自定义Term聚合条件
	 */
	@ApiParam("自定义Term聚合条件")
	private JSONArray aggs;
	/**
	 * 表达式
	 */
	@ApiParam("表达式")
	private String customQuery;
	/**
	 * 用户选择的模板id
	 */
	@ApiParam("用户选择的模板id")
	private Integer templateId;
	/**
	 * 预览的特征权重，比如[{k:'1',v:['1.1']}]
	 */
	@ApiParam("预览的特征权重，比如[{k:'1',v:['1.1']}]")
	private List<KVBean<Integer, Float>> featureWeights;
	@ApiParam("当前搜索时简易搜索还是复杂搜索")
	private int kwApi = 0;
	@ApiParam("限定id集合中的过滤")
	private List<String> ids = new ArrayList<>();
	/**
	 * 模糊 or 精确
	 *
	 * <p>默认精确
	 */
	private Integer precision = 1;
	private String description;
}

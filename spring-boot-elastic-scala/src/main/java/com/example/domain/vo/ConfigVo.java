package com.example.domain.vo;

import com.example.domain.bo.SearchPatternToBuild;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.cluster.health.ClusterIndexHealth;

import java.util.List;

/**
 * 配置vo
 *
 * @author shizeying
 * @date 2021/01/03
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ConfigVo {
	
	/** 搜索信息 */
	private SearchPatternToBuild searchInfo;
	/** 健康状况 */
	private List<ClusterIndexHealth> healthStatus;
	
}

package com.example.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.plus.entity.SnapshotGroup;
import com.example.mybatis.plus.entity.SnapshotGroupBO;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@SuppressWarnings("ALL")
@Mapper
public interface GraphConfSnapshotMapper extends BaseMapper<SnapshotGroup> {
	
	
	String querySql = "SELECT\n"
			+ " gcsg.create_date AS  create_date,\n"
			+ "       gcsg.update_date AS update_date,\n"
			+ "       gcsg.graph_conf_snapshot_id AS graph_conf_snapshot_id,\n"
			+ "       gcsg.focus_entity_name AS focus_entity_name,\n"
			+ "       gcsg.id AS  id,\n"
			+ "       gcsg.subject_name AS subject_name,\n"
			+ "       gcs.spa_id AS spa_id,\n"
			+ "       gcs.snapshot_config AS  snapshot_config,\n"
			+ "       gcs.create_at AS create_at,\n"
			+ "       gcs.update_at AS update_at,\n"
			+ "       gcs.remark AS remark,\n"
			+ "       gcs.name AS name\n"
			+ "\n"
			+ "from   group gcsg\n"
			+ "    left join snapshot gcs ON gcsg.graph_conf_snapshot_id=gcs.id "
			+ " ${ew.customSqlSegment}";
	String queryByGroupSql = "SELECT "
			+ " subject_name AS subjectName,"
			+ "count(1) AS count"
			+ " FROM "
			+ " snapshot_group "
			+ "GROUP BY  subject_name"
			+ " ${ew.customSqlSegment}";
	
	/**
	 * Example
	 *
	 * @param page    页面
	 * @param wrapper 包装器
	 * @return {@link Page< SnapshotGroupBO >}
	 */
	@Select(querySql)
	IPage<SnapshotGroupBO> selectByExample(
			IPage page, @Param(Constants.WRAPPER) Wrapper wrapper);
	
	/**
	 * 分组
	 *
	 * @param page    页面
	 * @param wrapper 包装器
	 * @return {@link Page<Map<String, Object>>}
	 */
	
	@Select(queryByGroupSql)
	IPage<Map<String, Object>> groupBySubjectName(IPage page,
			@Param(Constants.WRAPPER) Wrapper wrapper);

}

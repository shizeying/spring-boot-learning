package com.example.handle;

import com.example.domain.bo.GeneralBuild;
import com.example.utils.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;

/**
 * 一般构建处理程序
 *
 * @author shizeying
 * @date 2021/01/02
 */
@Slf4j
@MappedJdbcTypes({JdbcType.LONGVARCHAR})
@MappedTypes({List.class})
public class GeneralBuildsHandler extends BaseTypeHandler<List<GeneralBuild>> {
	/**
	 * 非null参数设置
	 *
	 * @param i
	 * 		我
	 * @param generalBuilds
	 * 		一般的构建
	 * @param jdbcType
	 * 		jdbc类型
	 * @param ps
	 * 		ps
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<GeneralBuild> generalBuilds, JdbcType jdbcType) {
		log.info("格式化list<GeneralBuild>");
		String generalBuildJson = JacksonUtil.bean2Json(generalBuilds);
		Try.run(() -> ps.setString(i, generalBuildJson))
		   .onFailure(Throwable::printStackTrace)
		   .get();
	}
	
	/**
	 * 得到nullable结果
	 *
	 * @param rs
	 * 		rs
	 * @param columnIndex
	 * 		列索引
	 *
	 * @return {@link List<GeneralBuild>}
	 */
	@Override
	public List<GeneralBuild> getNullableResult(ResultSet rs, String columnIndex) {
		String json = Try.of(() -> rs.getString(columnIndex))
		                 .onFailure(Throwable::printStackTrace)
		                 .get();
		if (Objects.nonNull(json)) {
			
			return JacksonUtil.json2BeanBySnakeCaseTypeReference(json, new TypeReference<List<GeneralBuild>>() {
			});
		}
		
		return Lists.newArrayList();
	}
	
	/**
	 * 得到nullable结果
	 *
	 * @param rs
	 * 		rs
	 * @param columnIndex
	 * 		列索引
	 *
	 * @return {@link List<GeneralBuild>}
	 */
	@Override
	public List<GeneralBuild> getNullableResult(ResultSet rs, int columnIndex) {
		
		String json = Try.of(() -> rs.getString(columnIndex))
		                 .onFailure(Throwable::printStackTrace)
		                 .get();
		
		
		if (Objects.nonNull(json)) {
			
			return JacksonUtil.json2BeanBySnakeCaseTypeReference(json, new TypeReference<List<GeneralBuild>>() {
			});
		}
		return Lists.newArrayList();
	}
	
	/**
	 * 得到nullable结果
	 *
	 * @param cs
	 * 		cs
	 * @param columnIndex
	 * 		列索引
	 *
	 * @return {@link List<GeneralBuild>}
	 */
	@Override
	public List<GeneralBuild> getNullableResult(CallableStatement cs, int columnIndex) {
		log.info("通过cs格式化数据");
		
		String json = Try.of(() -> cs.getString(columnIndex))
		                 .onFailure(Throwable::printStackTrace)
		                 .get();
		
		
		if (Objects.nonNull(json)) {
			return JacksonUtil.json2BeanBySnakeCaseTypeReference(json, new TypeReference<List<GeneralBuild>>() {
			});
		}
		return Lists.newArrayList();
	}
}

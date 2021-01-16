package com.example.handle;



import com.example.domain.bo.Attr;
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
 * attrs处理程序
 *
 * @author Administrator
 * @date 2021/01/02
 */
@Slf4j

@MappedJdbcTypes({JdbcType.LONGVARCHAR})
@MappedTypes({List.class})
public class AttrsHandler extends BaseTypeHandler<List<Attr>> {
	
	/**
	 * 非null参数设置
	 * 用于定义在Mybatis设置参数时该如何把Java类型的参数转换为对应的数据库类型
	 *
	 * @param i
	 * 		我
	 * @param attrs
	 * 		attrs
	 * @param jdbcType
	 * 		jdbc类型
	 * @param ps
	 * 		ps
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<Attr> attrs, JdbcType jdbcType) {
		log.info("格式化list<attr>");
		String attrJson = JacksonUtil.bean2Json(attrs);
		Try.run(() -> ps.setString(i, attrJson))
		   .onFailure(Throwable::printStackTrace)
		   .get();
	}
	
	/**
	 * 得到nullable结果
	 * 用于在Mybatis获取数据结果集时如何把数据库类型转换为对应的Java类
	 *
	 * @param rs
	 * 		rs
	 * @param columnIndex
	 * 		列索引
	 *
	 * @return {@link List<Attr>}
	 */
	@Override
	public List<Attr> getNullableResult(ResultSet rs, String columnIndex) {
		log.info("格式化list<attr>");
		String json = Try.of(() -> rs.getString(columnIndex))
		                 .onFailure(Throwable::printStackTrace)
		                 .get();
		if (Objects.nonNull(json)) {
			
			return JacksonUtil.json2BeanBySnakeCaseTypeReference(json, new TypeReference<List<Attr>>() {
			});
		}
		return Lists.newArrayList();
	}
	
	/**
	 * 得到nullable结果
	 * 用于在Mybatis通过字段位置获取字段数据时把数据库类型转换为对应的Java类型
	 *
	 * @param rs
	 * 		rs
	 * @param columnIndex
	 * 		列索引
	 *
	 * @return {@link List<Attr>}
	 */
	@Override
	public List<Attr> getNullableResult(ResultSet rs, int columnIndex) {
		log.info("rs格式化数据");
		String json = Try.of(() -> rs.getString(columnIndex))
		                 .onFailure(Throwable::printStackTrace)
		                 .get();
		if (Objects.nonNull(json)) {
			return JacksonUtil.json2BeanBySnakeCaseTypeReference(json, new TypeReference<List<Attr>>() {
			});
		}
		
		return Lists.newArrayList();
	}
	
	/**
	 * 得到nullable结果
	 * 用于Mybatis在调用存储过程后把数据库类型的数据转换为对应的Java类型
	 *
	 * @param cs
	 * 		cs
	 * @param columnIndex
	 * 		列索引
	 *
	 * @return {@link List<Attr>}
	 */
	@Override
	public List<Attr> getNullableResult(CallableStatement cs, int columnIndex) {
		log.info("通过cs格式化数据");
		
		String json = Try.of(() -> cs.getString(columnIndex))
		                 .onFailure(Throwable::printStackTrace)
		                 .get();
		if (Objects.nonNull(json)) {
			return JacksonUtil.json2BeanBySnakeCaseTypeReference(json, new TypeReference<List<Attr>>() {
			});
		}
		
		return Lists.newArrayList();
	}
	
}

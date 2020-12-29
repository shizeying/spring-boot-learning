package com.example.typehandle.handle;


import com.google.common.collect.Maps;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.util.Map;
import java.util.Objects;

/**
 * @author Administrator
 */
@Slf4j
@MappedJdbcTypes({JdbcType.JAVA_OBJECT})
@MappedTypes({Map.class})
public class MapTypeHandler extends BaseTypeHandler<Map<String, Object>> {
	private MapTypeHandler() {
	}
	
	/**
	 * 用于定义在Mybatis设置参数时该如何把Java类型的参数转换为对应的数据库类型
	 *
	 * @param ps
	 * 		当前的PreparedStatement对象
	 * @param i
	 * 		当前参数的位置
	 * @param parameter
	 * 		当前参数的Java对象
	 * @param jdbcType
	 * 		当前参数的数据库类型
	 *
	 * @throws SQLException
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType) {
		log.info("不需要");
	}
	
	
	/**
	 * 用于在Mybatis获取数据结果集时如何把数据库类型转换为对应的Java类型
	 *
	 * @param rs
	 * 		当前的结果集
	 * @param columnName
	 * 		当前的字段名称
	 *
	 * @return 转换后的Java对象
	 *
	 * @throws SQLException
	 */
	@Override
	public Map<String, Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		log.info("getNullableResult1");
		Object value = rs.getObject(columnName);
		
		Map<String, Object> map = Maps.newHashMap();
		
		if (Objects.nonNull(value)) {
			map.put(columnName, value);
			
		}
		return map;
	}
	
	/**
	 * 用于在Mybatis通过字段位置获取字段数据时把数据库类型转换为对应的Java类型
	 *
	 * @param rs
	 * 		当前的结果集
	 * @param columnIndex
	 * 		当前字段的位置
	 *
	 * @return 转换后的Java对象
	 *
	 * @throws SQLException
	 */
	@Override
	public Map<String, Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		log.info("getNullableResult2");
		
		return getStringObjectMap(columnIndex, rs.getObject(columnIndex), rs.getMetaData());
	}
	
	/**
	 * 用于Mybatis在调用存储过程后把数据库类型的数据转换为对应的Java类型
	 *
	 * @param cs
	 * 		当前的CallableStatement执行后的CallableStatement
	 * @param columnIndex
	 * 		当前输出参数的位置
	 *
	 * @return
	 *
	 * @throws SQLException
	 */
	@Override
	public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		log.info("getNullableResult3");
		return getStringObjectMap(columnIndex, cs.getObject(columnIndex), cs.getMetaData());
	}
	
	private Map<String, Object> getStringObjectMap(int columnIndex, Object object, ResultSetMetaData metaData) {
		String columnName = Try
				                    .of(() -> metaData.getColumnName(columnIndex))
				                    .onFailure(Throwable::printStackTrace)
				                    .get();
		Map<String, Object> map = Maps.newHashMap();
		if (Objects.nonNull(object)) {
			map.put(columnName, object);
			
		}
		return map;
	}
}

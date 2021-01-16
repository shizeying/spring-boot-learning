package com.example.handle;

import com.example.utils.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import io.vavr.control.Try;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@MappedTypes({List.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class ListTypeHandler extends BaseTypeHandler<List> {
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
	                                List parameter, JdbcType jdbcType) {
		// TODO Auto-generated method stub
		String param = JacksonUtil.bean2Json(parameter);
		Try.run(() -> ps.setString(i, param)).onFailure(Throwable::printStackTrace).get();
	}
	
	@Override
	public List getNullableResult(ResultSet rs, String columnName) {
		
		String str = Try.of(() -> rs.getString(columnName)).onFailure(Throwable::printStackTrace).get();
		
		
		if ("doc_types".equals(columnName)) {
			List<String> list = JacksonUtil.json2CustomByTypeReference(str, new TypeReference<List<String>>() {
			});
			return list;
		}
		
		List<Object> list = JacksonUtil.json2CustomByTypeReference(str, new TypeReference<List<Object>>() {
		});
		return list;
	}
	
	@Override
	public List getNullableResult(ResultSet rs, int columnIndex) {
		// TODO Auto-generated method stub
		String str = Try.of(() -> rs.getString(columnIndex)).onFailure(Throwable::printStackTrace).get();
		List<Object> list = JacksonUtil.json2CustomByTypeReference(str, new TypeReference<List<Object>>() {
		});
		return list;
	}
	
	@Override
	public List getNullableResult(CallableStatement cs, int columnIndex) {
		// TODO Auto-generated method stub
		String str = Try.of(() -> cs.getString(columnIndex)).onFailure(Throwable::printStackTrace).get();
		List<Object> list = JacksonUtil.json2CustomByTypeReference(str, new TypeReference<List<Object>>() {
		});
		return list;
	}
	
}

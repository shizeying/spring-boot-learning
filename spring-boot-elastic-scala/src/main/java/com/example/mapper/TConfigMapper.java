package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.TConfig;
import com.example.domain.bo.SearchPatternToBuild;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface TConfigMapper extends BaseMapper<TConfig> {
	Boolean updateById(@Param("id") Long id, @Param("updateDate") LocalDateTime updateDate, @Param("entity") SearchPatternToBuild entity);
}
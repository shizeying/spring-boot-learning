package com.example.typehandle.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.typehandle.entity.TestUsersEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shizeying
 */
@Repository
@Mapper
public interface TestUsersMapper extends BaseMapper<TestUsersEntity> {
	List<TestUsersEntity> find();
}

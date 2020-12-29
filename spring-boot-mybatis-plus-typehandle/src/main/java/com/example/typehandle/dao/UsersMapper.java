package com.example.typehandle.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.typehandle.entity.UsersEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsersMapper extends BaseMapper<UsersEntity> {
	List<UsersEntity> find();
}

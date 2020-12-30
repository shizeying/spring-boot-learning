package com.example.typehandle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.typehandle.dao.TestUsersMapper;
import com.example.typehandle.entity.TestUsersEntity;
import com.example.typehandle.service.TestUsersService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class TestUsersServiceImpl extends ServiceImpl<TestUsersMapper, TestUsersEntity> implements TestUsersService {
	@Override
	public List<TestUsersEntity> find() {
		return this.baseMapper.find();
	}
}

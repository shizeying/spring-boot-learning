package com.example.typehandle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.typehandle.dao.UsersMapper;
import com.example.typehandle.entity.UsersEntity;
import com.example.typehandle.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, UsersEntity> implements UsersService {
	@Override
	public List<UsersEntity> find() {
		return this.baseMapper.find();
	}
}

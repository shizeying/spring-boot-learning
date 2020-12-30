package com.example.typehandle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.typehandle.entity.TestUsersEntity;

import java.util.List;

public interface TestUsersService extends IService<TestUsersEntity> {
	List<TestUsersEntity> find();
}

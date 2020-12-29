package com.example.typehandle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.typehandle.entity.UsersEntity;

import java.util.List;

public interface UsersService extends IService<UsersEntity> {
	List<UsersEntity> find();
}

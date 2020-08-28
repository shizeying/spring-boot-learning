package com.example.mongodb.controller;

import com.example.mongodb.controller.qo.UserPortraitQo;
import com.example.mongodb.entity.UserPortraitEntity;
import com.example.mongodb.service.UserPortraitService;
import com.example.mongodb.utils.PageResult;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserPortraitController {
  @Autowired private UserPortraitService userPortraitService;

  @GetMapping("show")
  public PageResult<UserPortraitEntity> showUserPortraits(UserPortraitQo userPortraitQo) {

    return userPortraitService.showUserPortraits(
        userPortraitQo.getEntityId(),
        userPortraitQo.getKgName(),
        userPortraitQo.getPageSize(),
        userPortraitQo.getPageNum());
  }

  @PutMapping("update")
  public UserPortraitEntity updateUserPortraits(UserPortraitQo userPortraitQo) {

    return userPortraitService.update(
        userPortraitQo.getUsername(),
        userPortraitQo.getIds().get(0),
        userPortraitQo.getToAnnotates().get(0));
  }

  @PutMapping("insert")
  public List<UserPortraitEntity> saveUserPortraits(UserPortraitQo userPortraitQo) {

    return userPortraitService.batchAdd(
        userPortraitQo.getEntityId(),
        userPortraitQo.getUsername(),
        userPortraitQo.getKgName(),
        userPortraitQo.getToAnnotates());
  }

  @DeleteMapping("delete")
  public Long deleteUserPortraits(UserPortraitQo userPortraitQo) {

    return userPortraitService.batchDelete(userPortraitQo.getIds());
  }
}

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
        userPortraitQo.getDatabaseName(),
        userPortraitQo.getPageSize(),
        userPortraitQo.getPageNum());
  }

  @PutMapping("update")
  public UserPortraitEntity updateUserPortraits(UserPortraitQo userPortraitQo) {

    return userPortraitService.update(
        userPortraitQo.getUsername(),
        userPortraitQo.getId(),
        userPortraitQo.getToAnnotates().get(0));
  }

  @PutMapping("insert")
  public List<UserPortraitEntity> saveUserPortraits(UserPortraitQo userPortraitQo) {

    return userPortraitService.batchAdd(
        userPortraitQo.getEntityId(),
        userPortraitQo.getUsername(),
        userPortraitQo.getDatabaseName(),
        userPortraitQo.getToAnnotates());
  }

  @DeleteMapping("delete")
  public Long deleteUserPortraits(List<String> ids) {

    return userPortraitService.batchDelete(ids);
  }
}

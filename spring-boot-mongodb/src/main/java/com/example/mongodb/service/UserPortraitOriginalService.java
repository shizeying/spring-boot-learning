package com.example.mongodb.service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.example.mongodb.entity.UserPortrait;
import com.example.mongodb.entity.UserPortraitEntity;
import com.example.mongodb.utils.PageResult;
import com.mongodb.MongoClientSettings;
import java.util.List;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.data.domain.Page;

/**
 * 用户画像接口
 *
 * @author shizeying
 * @date 2020/08/28
 */
public interface UserPortraitOriginalService {

  /**
   * 显示用户画像
   *
   * @param entityId 实体的id
   * @param databaseName 对应的图谱
   * @param pageSize
   * @param pageNum
   * @return {@link Page<UserPortraitEntity>}
   */
  PageResult<UserPortrait> showUserPortraits(
      Long entityId, String databaseName, Integer pageSize, Integer pageNum);

  /**
   * 批处理delete
   *
   * @param ids 用户画像id
   * @return {@link String}
   */
  long batchDelete(List<String> ids);

  /**
   * 更新
   *
   * @param username 用户名
   * @param id 用户画像id
   * @param toAnnotate 研判批注
   * @return {@link UserPortraitEntity}
   */
  UserPortrait update(String username, String id, String toAnnotate);

  /**
   * 批处理add
   *
   * @param entityId 实体的id
   * @param username 用户名
   * @param kgName 公斤的名字
   * @param toAnnotates 研判批注
   * @return {@link List<UserPortraitEntity>}
   */
  int batchAdd(Long entityId, String username, String kgName, List<String> toAnnotates);

  /**
   * 编解码器注册表 这个方法很重要，是保证UserPortrait中 @BsonProperty会转换成对应的方法的重要方法
   *
   * @return {@link CodecRegistry}
   */
  default CodecRegistry codecRegistry() {
    CodecRegistry pojoCodecRegistry =
        fromProviders(PojoCodecProvider.builder().automatic(true).build());
    return fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
  }
}

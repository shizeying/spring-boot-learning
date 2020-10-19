package com.example.mongodb.service;

import com.example.mongodb.entity.UserPortraitEntity;
import com.example.mongodb.utils.PageResult;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 用户画像接口
 *
 * @author shizeying
 * @date 2020/08/28
 */
public interface UserPortraitService {

    /**
     * 显示用户画像
     *
     * @param entityId 实体的id
     * @param databaseName   对应的图谱
     * @param pageSize
     * @param pageNum
     * @return {@link Page<UserPortraitEntity>}
     */
    PageResult<UserPortraitEntity> showUserPortraits(Long entityId, String databaseName, Integer pageSize, Integer pageNum);

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
     * @param username   用户名
     * @param id         用户画像id
     * @param toAnnotate 研判批注
     * @return {@link UserPortraitEntity}
     */
    UserPortraitEntity update(String username, String id, String toAnnotate);

    /**
     * 批处理add
     *
     *
     * @param entityId    实体的id
     * @param databaseName      公斤的名字
     * @param toAnnotates 研判批注
     * @param username    用户名
     * @return {@link List<UserPortraitEntity>}
     */
    List<UserPortraitEntity> batchAdd(Long entityId, String username, String databaseName, List<String> toAnnotates);


}

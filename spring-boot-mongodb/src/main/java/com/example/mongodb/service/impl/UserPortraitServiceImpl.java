package com.example.mongodb.service.impl;

import com.example.mongodb.entity.UserPortraitEntity;
import com.example.mongodb.repository.UserPortraitRepository;
import com.example.mongodb.service.UserPortraitService;
import com.example.mongodb.utils.MongoPageHelper;
import com.example.mongodb.utils.PageResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户画像服务实现
 *
 * @author shizeying
 * @date 2020/08/28
 */
@Service
public class UserPortraitServiceImpl implements UserPortraitService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserPortraitRepository repository;
    @Autowired
    private MongoPageHelper mongoPageHelper;
    private final static String COLLECTION_NAME = "kgms_trail_content";

    /**
     * 显示用户画像
     *
     * @param entityId 实体的id
     * @param kgName   对应的图谱
     * @param pageSize
     * @param pageNum
     * @return {@link List<UserPortraitEntity>}
     */
    @Override
    public PageResult<UserPortraitEntity> showUserPortraits(Long entityId, String kgName,
                                                            Integer pageSize, Integer pageNum) {
        Query condition = new Query();

        condition.addCriteria(Criteria.where("entity_id").is(entityId))
                .addCriteria(Criteria.where("kg_name").is(kgName));
        Sort sort = Sort.by(Direction.DESC, "update_time");

        return mongoPageHelper.pageQuery(condition, UserPortraitEntity.class, Optional.ofNullable(pageSize).orElse(10),
                Optional.ofNullable(pageNum).orElse(1), sort);

    }

    /**
     * @param ids 用户画像id
     * @return long
     */
    @Override
    public long batchDelete(List<String> ids) {
        Query condition = new Query();
        List<ObjectId> objectIds = ids.stream().map(ObjectId::new).collect(Collectors.toList());
        condition.addCriteria(Criteria.where("_id").in(objectIds));

        return mongoTemplate.remove(condition, COLLECTION_NAME).getDeletedCount();

    }

    /**
     * 更新
     *
     * @param username   用户名
     * @param id         用户画像id
     * @param toAnnotate 研判批注
     * @return {@link UserPortraitEntity}
     */
    @Override
    public UserPortraitEntity update(String username, String id, String toAnnotate) {

        if (repository.existsById(id)) {
            UserPortraitEntity entity = repository.findById(id).get();
            entity.setUsername(username);
            entity.setToAnnotate(toAnnotate);
            entity.setUpdateTime(LocalDateTime.now());

            return repository.save(entity);
        } else {
            return null;
        }


    }

    /**
     * 批处理add
     * 天剑
     *
     * @param entityId    实体的id
     * @param username    用户名
     * @param kgName      公斤的名字
     * @param toAnnotates 研判批注
     * @return {@link List<UserPortraitEntity>}
     */
    @Override
    public List<UserPortraitEntity> batchAdd(Long entityId, String username, String kgName, List<String> toAnnotates) {
        List<UserPortraitEntity> userPortraitEntities = new ArrayList<>();
        toAnnotates.stream().forEach(toAnnotate -> {
            UserPortraitEntity entity = new UserPortraitEntity();
            entity.setEntityId(entityId);
            entity.setUsername(username);
            entity.setKgName(kgName);
            entity.setInsertTime(LocalDateTime.now());
            entity.setUpdateTime(LocalDateTime.now());
            entity.setToAnnotate(toAnnotate);
            userPortraitEntities.add(entity);
        });

        return repository.saveAll(userPortraitEntities);
    }
}

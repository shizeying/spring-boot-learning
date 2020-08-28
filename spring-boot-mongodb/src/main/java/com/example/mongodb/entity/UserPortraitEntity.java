package com.example.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;


/**
 * 用户画像批注的实体
 *
 * @author shizeying
 * @date 2020/08/28
 */
@Data
@Accessors(chain = true)
@Document(collection = "kgms_trail_content")
public class UserPortraitEntity {
    /**
     * 研判记录id
     */
    @Id
    private String id;
    /**
     * 用户实体id
     */
    @Field("entity_id")
    private Long entityId;
    /**
     * 操作用户
     */
    @Field("username")
    private String username;
    /**
     * 对应mongo集合
     */
    @Field("kg_name")
    private String kgName;
    /**
     * 插入时间
     */
    @Field("insert_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime insertTime;
    /**
     * 更新时间
     */
    @Field("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime updateTime;
    /**
     * 研判批注
     */
    @Field("to_annotate")
    private String toAnnotate;


}

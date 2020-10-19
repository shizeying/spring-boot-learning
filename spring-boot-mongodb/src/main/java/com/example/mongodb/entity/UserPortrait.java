package com.example.mongodb.entity;

import com.example.mongodb.utils.ObjectIdSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * 原生mongodb操作方法
 *
 * @author shizeying
 * @date 2020/08/28
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@BsonDiscriminator
public class UserPortrait {

  @Id
  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId id;

  @BsonProperty(value = "entity_id")
  private Long entityId;

  @BsonProperty(value = "username")
  private String username;

  @BsonProperty(value = "database_name")
  private String databaseName;

  @BsonProperty(value = "insert_time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime insertTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @BsonProperty(value = "update_time")
  private LocalDateTime updateTime;

  @BsonProperty(value = "to_annotate")
  private String toAnnotate;
}

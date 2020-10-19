package com.example.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author shizeying
 * @date 2020/08/28
 */
@Data
@Accessors(chain = true)
@Document(collection = "content")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPortraitEntity {

  @Id private String id;

  @Field("entity_id")
  private Long entityId;

  @Field("username")
  private String username;

  @Field("database_name")
  private String databaseName;

  @Field("insert_time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime insertTime;

  @Field("update_time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime updateTime;

  @Field("to_annotate")
  private String toAnnotate;
}

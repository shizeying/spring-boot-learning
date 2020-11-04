package com.example.kafka.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FavoritesEntity implements Serializable {
  private static final long serialVersionUID = -5457103531947646047L;
  private String favoritesName;
  private String id;
  @NotNull(message = "查询的索引不能为空")
  private String mappingName;
  @NotNull(message = "查询关键词不能为空")
  private String kw;
  private String categoryTags;
  private String[] ids;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date toSaveTime;
  
}

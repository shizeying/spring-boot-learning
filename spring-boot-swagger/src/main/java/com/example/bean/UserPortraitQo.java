package com.example.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class UserPortraitQo implements Serializable {
  /** 研判批注集合 */
  @ApiModelProperty(
      name = "toAnnotates",
      value = "研判批注集合",
      required = true,
      dataType = "List",
      example = "[\"str1\", \"str2\", \"str3\"]")
  private List<String> toAnnotates;
  /** 研判记录id */
  @ApiModelProperty(
      name = "id",
      value = "研判记录ids;如果是更新则id必填",
      dataType = "String",
      example = "5f6efe1a76bca06b044b1e15")
  private String id;

  /** 用户实体id */
  @NotNull(message = "操作用户不能为空")
  @ApiModelProperty(value = "用户实体id", dataType = "Long", required = true, example = "117")
  private Long entityId;
  /** 操作用户 */
  @ApiModelProperty(value = "操作用户", dataType = "String", example = "test")
  @NotNull(message = "操作用户不能为空")
  private String username;
  /** 对应mongo集合 */
  @NotNull(message = "对应mongo集合不能为空")
  @ApiModelProperty(
      value = "对应mongo集合",
      required = true,
      dataType = "String",
      example = "kgms_default_user_graph_174c4e9a4a8")
  private String kgName;
}

package com.example.common.user.dto;


import com.example.common.common.validate.annotation.EnumValid;
import com.example.common.common.validate.group.Add;
import com.example.common.common.validate.group.Delete;
import com.example.common.common.validate.group.Update;
import com.example.common.user.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements Serializable {

  @NotNull(message = "编号不能为空",groups = {Update.class, Delete.class})
  private Long id;

  @NotBlank(message = "用户名不能为空",groups = {Add.class})
  private String userName;

  @NotBlank(message = "姓名不能为空",groups = {Add.class})
  private String realName;

  @NotBlank(message = "密码不能为空",groups = {Add.class})
  @Size(max=32,min=6,message = "密码长度要在6-32之间",groups = {Add.class})
  private String password;

  @NotNull(message = "性别不能为空",groups = {Add.class})
  @EnumValid(target = Gender.class, message = "性别取值必须为0或者1",groups = {Add.class,Update.class})
  private Integer gender;

  @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$",message = "不满足邮箱正则表达式",groups = {Add.class,Update.class})
  private String email;



}

package com.example.common.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {


  private Long id;


  private String userName;

  private String realName;

  private String password;

  private Integer gender;

  private String email;


}

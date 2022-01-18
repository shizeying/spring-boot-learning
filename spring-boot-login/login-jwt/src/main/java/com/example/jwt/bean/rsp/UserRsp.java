package com.example.jwt.bean.rsp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRsp {

  private Long id;
  private String username;

  private String nickname;

  private String token;


}

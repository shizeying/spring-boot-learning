package com.example.jwt.constant;

public interface BaseErrorInfoInterface {
  /** 错误码
   * @return*/
  int getResultCode();

  /** 错误描述*/
  String getResultMsg();

}

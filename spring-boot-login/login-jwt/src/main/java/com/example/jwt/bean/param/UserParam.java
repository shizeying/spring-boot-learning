package com.example.jwt.bean.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("用户登录参数")
public class UserParam {


    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("登录密码（后续需要加密）")
    private String password;

    @ApiModelProperty("ukey（后续需要验证）")
    private String ukey;
    
}

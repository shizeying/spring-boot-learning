package com.example.mongodb.controller.qo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
public class UserPortraitQo {
    /**
     */
    @Setter
    @Getter
    private String id;


    @Getter
    @Setter
    private int  pageSize;
    @Getter
    @Setter
    private int pageNum;
    /**
     * 用户实体id
     */
    @Getter
    @Setter
    private Long entityId;
    /**
     * 操作用户
     */
    @Getter
    @Setter
    private String username;
    /**
     * 对应mongo集合
     */
    @Getter
    @Setter
    private String databaseName;
    
    /**
     * 研判批注集合
     */
    @Getter
    @Setter
    private List<String> toAnnotates;


}

package com.example.hive2es.entity.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 基础的实体：实体表需要继承
 *
 * @author shizeying
 * @date 2020/09/03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity extends BasicEntity implements Serializable {

    /**
     * 名称
     */
    private String name;
    /**
     * abs
     */
    private String abs;
    /**
     * 来源
     */
    private String origin;
    /**
     * 图片
     */
    private String image;
    /**
     * 识别标签
     */
    private String identifyTag;

    /**
     * 经度
     */
    private Double lon;
    /**
     * 纬度
     */
    private Double lat;

    /**
     * gis地址
     */
    private String gisAdress;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 实体标签
     */
    private String entityTag;
}

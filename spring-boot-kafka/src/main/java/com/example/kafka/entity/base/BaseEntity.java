package com.example.kafka.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 基础的实体：实体表需要继承
 *
 * @author shizeying
 * @date 2020/09/03
 */
@Getter
@SuppressWarnings("ALL")
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity extends BasicEntity implements Serializable {

    /**
     * 名称
     */
    private String name;
    /**
     * 简介
     */
    private String abs;
    
    /**
     * 来源
     */
    private String origin;
    
    /**
     * 照片
     */
    private String image;
    /**
     * 唯一标识
     */
    private String meaningTag;
    /**
     * 消歧表示
     */
    private String identifyTag;

    /**
     * 经度
     */
    @Setter
    private Double lon;
    /**
     * 纬度
     */
    @Setter
    private Double lat;

    /**
     * gis地址名
     */
    private String gisAdress;
    /**
     * 开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Setter
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Setter
    private Date endTime;
    /**
     * 实体标签
     */
    private String entityTag;
    /**
     * 实体类型
     */
    private String entityType;
    @Setter
    private Map<String,Object> attrMap;
    
    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }
    
    public void setAbs(String abs) {
        this.abs = abs != null ? abs.trim() : null;
    }
    
    public void setOrigin(String origin) {
        this.origin = origin != null ? origin.trim() : null;
    }
    
    public void setImage(String image) {
        this.image = image != null ? image.trim() : null;
    }
    
    public void setMeaningTag(String meaningTag) {
        this.meaningTag = meaningTag != null ? meaningTag.trim() : null;
    }
    
    public void setIdentifyTag(String identifyTag) {
        this.identifyTag = identifyTag != null ? identifyTag.trim() : null;
    }
    
    public void setGisAdress(String gisAdress) {
        this.gisAdress = gisAdress != null ? gisAdress.trim() : null;
    }
    
    public void setEntityTag(String entityTag) {
        this.entityTag = entityTag != null ? entityTag.trim() : null;
    }
    
    public void setEntityType(String entityType) {
        this.entityType = entityType != null ? entityType.trim() : null;
    }
}

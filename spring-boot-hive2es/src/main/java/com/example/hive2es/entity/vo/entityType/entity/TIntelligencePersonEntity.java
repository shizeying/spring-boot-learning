package com.example.hive2es.entity.vo.entityType.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.hive2es.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author shizeying
 */
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TIntelligencePersonEntity extends BaseEntity implements Serializable {

    private String gender;

    private Integer height;

    private Integer age;
    private String position;
    private String englishName;
    private String winning;
    private String familyName;
    private String givenName;
    private String deathDate;
    private String bloodType;
    private String constellation;

    private Double weight;
    private String country;
    private String national;
    private String degree;
    private String meaningTag;
    private String accomplishment;
    private String expertise;
    private String profession;
    private String feature;
    private String religion;
    private String url;
}

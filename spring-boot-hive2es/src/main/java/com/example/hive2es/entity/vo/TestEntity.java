package com.example.hive2es.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.hive2es.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestEntity extends BaseEntity {

    private String test;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    ////@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    //private Date createdAt;
    //
    //public void setCreatedAt(Object createdAt) {
    //    this.createdAt = (Date) createdAt;
    //}
}

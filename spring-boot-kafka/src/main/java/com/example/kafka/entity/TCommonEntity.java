package com.example.kafka.entity;

import com.example.kafka.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TCommonEntity extends BaseEntity implements Serializable {}

package com.example.kafka.entity;

import com.example.kafka.entity.base.RelationEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TCommonRelation extends RelationEntity {
}

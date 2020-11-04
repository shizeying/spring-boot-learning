package com.example.kafka.entity;

import com.example.kafka.entity.base.RelationEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.Map;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventRelation extends RelationEntity {

  public EventRelation(
      String entityName,
      Date timeFrom,
      Date timeTo,
      String relName,
      String valueName,
      String entityType,
      String valueType,
      Map<String, Object> edgeMap) {
    super(entityName, timeFrom, timeTo, relName, valueName, entityType, valueType, edgeMap);
  }

  public EventRelation() {}
}

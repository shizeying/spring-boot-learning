package com.example.kafka.entity;

import com.example.kafka.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventEntity extends BaseEntity implements Serializable {
  private List<EventRelation> relations;
}

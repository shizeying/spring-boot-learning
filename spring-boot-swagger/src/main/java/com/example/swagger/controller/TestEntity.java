package com.example.swagger.controller;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class TestEntity {
  
  @NotBlank
  private String kgName;
  
  @NotBlank private Long id; // 定义域实体id
  @NotBlank private String attrName; // 私有对象属性名称
  @NotBlank private List<Long> ids; // 值域实体ids
}

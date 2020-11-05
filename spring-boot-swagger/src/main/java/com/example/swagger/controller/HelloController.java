package com.example.swagger.controller;

import io.swagger.annotations.Api;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "日期管理API")
public class HelloController {
  
  //@ApiImplicitParams({
  //    @ApiImplicitParam(
  //        name = "kgName",
  //        required = true,
  //        dataType = "string",
  //        paramType = "query",
  //        value = "图谱名称"),
  //    @ApiImplicitParam(
  //        name = "id",
  //        required = true,
  //        dataType = "Long",
  //        paramType = "form",
  //        value = "定义域实体id"),
  //    @ApiImplicitParam(
  //        name = "attrName",
  //        required = true,
  //        dataType = "String",
  //        paramType = "form",
  //        value = "私有对象属性名称"),
  //    @ApiImplicitParam(
  //        name = "ids",
  //        required = true,
  //        dataType = "string",
  //        paramType = "form",
  //        value = "值域实体ids")
  //})
  @PostMapping("show")
  public TestEntity show(TestEntity test) {
    return test;
  }

  public static void main(String[] args) {
    String id =" 1";
    List<Long> ids  =
        Arrays.stream(Optional.of(id.trim()).map(w -> w.split(",")).get()).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
   
    System.out.println(ids);
  }
}

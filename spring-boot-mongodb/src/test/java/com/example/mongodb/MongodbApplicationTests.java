package com.example.mongodb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//import com.example.mongodb.config.MongoUtil;
import com.example.mongodb.entity.UserPortraitEntity;
import com.example.mongodb.service.UserPortraitService;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootTest
class MongodbApplicationTests {
  @Autowired private UserPortraitService service;

  @Autowired private MongoTemplate mongoTemplate;

  @Test
  void contextLoads() {}

  @Test
  public void testMONG() {
  
   
  }

  @Test
  public void show() {

    assertNotNull(
        service.batchAdd(
            1L,
            "test",
            "test_",
            Arrays.asList("zhang", "zhang2", "zhang2", "zhang2", "shizeying")));
    assertNotNull(service.batchAdd(1L, "szy", "test_", Arrays.asList("shizeying")));
    assertThat(service.showUserPortraits(1L, "test_", null, null));
  }

  @Test
  void setMongoTemplateLike() {
    Query condition = new Query();
    // TODO like '%t'
    // Criteria annotate = Criteria.where("to_annotate").regex(Pattern.compile("^z.*$"));
    // TODO like '%t%'
    Criteria annotate = Criteria.where("to_annotate").regex(Pattern.compile("^.*z.*$"));
    // TODO like 't%'
    // Criteria annotate = Criteria.where("to_annotate").regex(Pattern.compile("^.*g.*$"));

    assertNotNull(find(condition.addCriteria(annotate)));
  }

  @Test
  void setMongoTemplateAnd() {
    Query condition = new Query();
    Criteria criteria = new Criteria();
    // TODO condition1 and condition2 and condition3
    // criteria.andOperator(
    //        Criteria.where("to_annotate").regex(Pattern.compile("^.*g.*$")),
    //        Criteria.where("id").is(new ObjectId("5f4a22283840722faf190d62"))
    //        );
    // TODO condition1 or condition2
    criteria.andOperator(
        Criteria.where("to_annotate").regex(Pattern.compile("^.*g.*$")),
        Criteria.where("username").is("szy"));
    condition.addCriteria(criteria);

    assertNotNull(find(condition));
  }

  @Test
  void setMongoTemplateOr() {
    Query condition = new Query();
    Criteria criteria = new Criteria();
    // TODO  condition1 or  condition2
    criteria.orOperator(
        Criteria.where("id").is("5f4a22283840722faf190d62"), Criteria.where("username").is("szy"));
    condition.addCriteria(criteria);
    assertNotNull(find(condition));
  }

  private List<UserPortraitEntity> find(Query condition) {
    return mongoTemplate.find(condition, UserPortraitEntity.class);
  }
}

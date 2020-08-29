package com.example.mongodb;

import com.example.mongodb.entity.UserPortraitEntity;
import com.example.mongodb.service.UserPortraitService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MongodbApplicationTests {
    @Autowired
    private UserPortraitService service;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void show() {

        assertNotNull(service.batchAdd(1L, "test", "test_", Arrays.asList("zhang", "zhang2", "zhang2", "zhang2")));
        assertThat(service.showUserPortraits(1L, "test_", null, null));

    }

    @Test
    void setMongoTemplateLike() {
        Query condition = new Query();
        //TODO like '%t'
        //Criteria annotate = Criteria.where("to_annotate").regex(Pattern.compile("^z.*$"));
        //TODO like '%t%'
        //Criteria annotate = Criteria.where("to_annotate").regex(Pattern.compile("^.*z.*$"));
        //TODO like 't%'
        Criteria annotate = Criteria.where("to_annotate").regex(Pattern.compile("^.*g.*$"));

        assertTrue(mongoTemplate.exists(condition.addCriteria(annotate), UserPortraitEntity.class));
    }
    @Test
    void setMongoTemplateAnd(){
        Query condition = new Query();
        Criteria criteria = new Criteria();
        //TODO condition1 and condition2 and condition3
        //criteria.andOperator(
        //        Criteria.where("to_annotate").regex(Pattern.compile("^.*g.*$")),
        //        Criteria.where("id").is(new ObjectId("5f4a22283840722faf190d62"))
        //        );
        //TODO condition1 or condition2
        criteria.andOperator(
                Criteria.where("to_annotate").regex(Pattern.compile("^.*g.*$")),
                Criteria.where("id").is(new ObjectId("5f4a22283840722faf190d62"))
        );
        assertTrue(mongoTemplate.exists(condition.addCriteria(criteria), UserPortraitEntity.class));
    }
}

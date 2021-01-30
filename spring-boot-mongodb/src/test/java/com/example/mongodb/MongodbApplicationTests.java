package com.example.mongodb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.mongodb.entity.UserPortrait;
import com.example.mongodb.entity.UserPortraitEntity;
import com.example.mongodb.service.UserPortraitService;
import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootTest
class MongodbApplicationTests {
	
	@Autowired
	private UserPortraitService service;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private MongoClient mongoClient;
	
	@Test
	void contextLoads() {
	}
	
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
		Long id = -1L;
		String reId = "gMaVlexJyP6Dx6pO4nA93Nk7";
		String kgName = "test";
		try {
			System.out.println(reId + "  " + kgName);
			Document query = new Document();
			query.append("meta_data.reId", reId);
			// gMaVlexJyP6Dx6pO4nA93Nk7
			
			MongoCursor<Document> iterator =
					mongoClient.getDatabase(kgName).getCollection("basic_info").find(query).iterator();
			
			while (iterator.hasNext()) {
				Document next = iterator.next();
				id = next.getLong("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			id = -1L;
		}
		// Query condition = new Query();
		// Criteria criteria = new Criteria();
		//// TODO  condition1 or  condition2
		// criteria.orOperator(
		//    Criteria.where("id").is("5f4a22283840722faf190d62"),
		// Criteria.where("username").is("szy"));
		// condition.addCriteria(criteria);
		// assertNotNull(find(condition));
	}
	
	@Test
	public void save() {
		String kgName = "test";
		UserPortraitEntity user = new UserPortraitEntity();
		user.setUsername("123");
		user.setEntityId(1L);
		
		
	}
	
	
	@Test
	public void setMongoClient() {
		UserPortrait user = new UserPortrait();
		user.setUsername("123");
		user.setEntityId(1L);
		Gson gson = new Gson();
		
		//ClassModel<UserPortrait> userModel = ClassModel.builder(UserPortrait.class).enableDiscriminator(true).build();
		//
		//PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().register(userModel).build();
		//
		//CodecRegistry pojoCodecRegistry = CodecRegistries
		//    .fromRegistries(defaultCodecRegistry, fromProviders(pojoCodecProvider));
		
	}
	
	
	private List<UserPortraitEntity> find(Query condition) {
		
		return mongoTemplate.find(condition, UserPortraitEntity.class);
	}
}

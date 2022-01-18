package com.example.jpa;

import com.alibaba.fastjson.JSON;
import com.example.jpa.repository.CategoryRuleRepository;
import com.example.utils.config.JacksonUtil;
import com.example.utils.function.FunctionalInterfaceUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@SpringBootTest
class SpringBootJpaH2ApplicationTests {
	
	@Test
	void contextLoads() {
	}
	
	@Autowired
	private CategoryRuleRepository repository;
	@Autowired
	private EntityManager entityManager;
	
	public static void main(String[] args) throws InterruptedException {
		
		String str = "{\"key\":1}";
		String json = "{\n" +
				              "  \"key:\": 1,\n" +
				              "  \"key2\": \"{\\\"key:\\\":1}\"\n" +
				              "}";
		
		// jsonLeaf(JacksonUtil.readJson(json));
		
		final List<Object> a1 = Lists.newArrayList("a", str)
				.stream()
				.map(a -> FunctionalInterfaceUtils.wrap(JSON::parse, a))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		jsonLeaf(JacksonUtil.readJson(str));
		// final Object wrap = FunctionalInterfaceUtils.wrap(JSON::parse, "a");
		// Lists.newArrayList(str)
		// 		     .stream()
		// 				     .map(FunctionalInterfaceUtils.toMapFunc)
		// CompletableFuture.runAsync(() -> Try.of(() ->
		// 		                                        JSON.parse(JSON.parse(JacksonUtil.readJson(str).toString()).toString()))
		// 		.onFailure(err -> new MyErrorException(err,str))
		// 		.getOrNull()
		//
		// ).whenComplete((key, error) -> {
		// 	System.out.println(key);
		// });
		// ;
		// Thread.sleep(50000);
		
		
	}
	
	public static void AtomicReferenceTest() {
		AtomicReference<Thread> cas = new AtomicReference<Thread>();
		
	}
	
	public static void jsonLeaf(JsonNode node) {
		if (node.isValueNode()) {
			
			return;
		}
		
		if (node.isObject()) {
			Iterator<Map.Entry<String, JsonNode>> it = node.fields();
			while (it.hasNext()) {
				Map.Entry<String, JsonNode> entry = it.next();
				jsonLeaf(entry.getValue());
			}
		}
		
		if (node.isArray()) {
			Iterator<JsonNode> it = node.iterator();
			while (it.hasNext()) {
				jsonLeaf(it.next());
			}
		}
	}
	
	// @Transactional
	// @Test
	// public void setRepository() {
	// 	// final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	// 	// final CriteriaQuery<CategoryRuleEntity> query = cb.createQuery(CategoryRuleEntity.class);
	// 	// final Root<CategoryRuleEntity> root = query.from(CategoryRuleEntity.class);
	// 	// final Root<TestEntity> from = query.from(TestEntity.class);
	// 	// root.join(CategoryRuleEntity_.testEntityList, JoinType.LEFT);
	// 	// query.distinct(true);
	// 	// final CompoundSelection<CategoryRuleEntity> construct = cb.construct(CategoryRuleEntity.class,root);
	// 	// query.select(construct)
	// 	// 		// .where(cb.equal(from.get(TestEntity_.TYPE_NAME), "3"))
	// 	// ;
	// 	final List<CategoryRuleEntity> all =
	// 	// entityManager.createQuery(query)
	// 	// 		.getResultList();
	//
	//
	// 			repository.findAll((root, query, cb) -> {
	//
	// 		final Root<TestEntity> from = query.from(TestEntity.class);
	// 		// final ListJoin<CategoryRuleEntity, TestEntity> join = root
	// 		// 		.joinList(CategoryRuleEntity_.TEST_ENTITY_LIST, JoinType.LEFT);
	// 		// join.on(cb.equal(root.get(CategoryRuleEntity_.ID),
	// 		// 		from.get(TestEntity_.CATEGORY_RULE_ID)));
	//
	//
	// 		// final Predicate predicate = cb.equal(
	// 		//
	// 		// 		                                     .get(TestEntity_.TYPE_NAME)
	// 		// 		,
	// 		//
	// 		//
	// 		//
	// 		// 		"3");
	// 		// root.joinList(CategoryRuleEntity_.TEST_ENTITY_LIST, JoinType.RIGHT);
	//
	// 		query.distinct(true);
	//
	// 		// query.where();
	// 		// return cb.equal(			root.get(CategoryRuleEntity_.TEST_ENTITY_LIST).as(TestEntity.class).get(TestEntity_.CATEGORY_RULE_ID), "3");
	//
	//
	// 	});
	// 	for (final CategoryRuleEntity categoryRuleEntity : all) {
	// 		for (final TestEntity testEntity : categoryRuleEntity.getTestEntityList()) {
	// 			System.err.println(testEntity);
	// 			System.out.println(testEntity.getId());
	// 		}
	// 	}
	//
	//
	// }
	
}

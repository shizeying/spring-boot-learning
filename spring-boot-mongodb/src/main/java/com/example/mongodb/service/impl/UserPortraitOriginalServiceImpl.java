package com.example.mongodb.service.impl;

import com.example.mongodb.entity.UserPortrait;
import com.example.mongodb.service.UserPortraitOriginalService;
import com.example.mongodb.utils.PageResult;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.result.UpdateResult;
import io.vavr.Function2;
import io.vavr.Function4;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author shizeying
 */
@SuppressWarnings("ALL")
@Service
@Slf4j
public class UserPortraitOriginalServiceImpl implements UserPortraitOriginalService {
	
	private static final String COLLECTION_NAME = "content";
	private static final int FIRST_PAGE_NUM = 1;
	private static final String DATA_BASENAME = "picture";
	@Autowired
	MongoConverter mongoConverter;
	@Qualifier("getMongoClient")
	@Autowired
	private MongoClient mongoClient;
	
	@Override
	public PageResult<UserPortrait> showUserPortraits(
			Long entityId, String databaseName, Integer pageSize, Integer pageNum) {
		MongoDatabase db = mongoClient.getDatabase(DATA_BASENAME);
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("entity_id", entityId);
		dbObject.put("database_name", databaseName);
		
		FindIterable<UserPortrait> documents =
				db.withCodecRegistry(codecRegistry())
						.getCollection(COLLECTION_NAME, UserPortrait.class)
						.find(dbObject)
						.sort(new BasicDBObject("update_time", -1));
		
		long total = Stream.of(documents.spliterator()).count();
		if (pageNum <= 0 || pageNum > (total / pageSize)) {
			pageNum = FIRST_PAGE_NUM;
		}
		List<UserPortrait> userPortraitEntityList =
				documents.skip(pageSize * (pageNum - 1)).limit(pageSize).into(new ArrayList<>());
		
		return new PageResult<UserPortrait>()
				.setTotal(total)
				.setPages((int) (total / pageSize))
				.setPageSize(pageSize)
				.setPageNum(pageNum)
				.setList(userPortraitEntityList);
	}
	
	@Override
	public long batchDelete(List<String> ids) {
		MongoCollection<Document> picture =
				mongoClient.getDatabase(COLLECTION_NAME).getCollection(COLLECTION_NAME);
		return ids.stream()
				.map(ObjectId::new)
				.map(Filters::eq)
				.map(id -> picture.deleteOne(id).getDeletedCount())
				.count();
	}
	
	@Override
	public UserPortrait update(String username, String id, String toAnnotate) {
		
		MongoCollection<UserPortrait> picture =
				mongoClient
						.getDatabase(DATA_BASENAME)
						.withCodecRegistry(codecRegistry())
						.getCollection(COLLECTION_NAME, UserPortrait.class);
		Bson append = Filters.eq(new ObjectId(id));
		Document updateDocument =
				new Document("$set", new Document("username", username).append("to_annotate", toAnnotate));
		
		UpdateResult updateResult = picture.updateOne(append, updateDocument);
		
		return updateResult.getModifiedCount() > 0 ? picture.find(append).first() : null;
	}
	
	@Override
	public int batchAdd(
			Long entityId, String username, String databaseName, List<String> toAnnotates) {
		MongoCollection<UserPortrait> kgmsFilePicture =
				mongoClient
						.getDatabase(DATA_BASENAME)
						.withCodecRegistry(codecRegistry())
						.getCollection(COLLECTION_NAME, UserPortrait.class);
		
		
		Function4<Long, String, String, String, UserPortrait> function3 =
				(ei, un, db, annotate) -> new UserPortrait()
						.setEntityId(ei)
						.setUsername(un)
						.setDatabaseName(db)
						.setInsertTime(LocalDateTime.now())
						.setUpdateTime(LocalDateTime.now())
				.setToAnnotate(annotate);
	Function<String,UserPortrait> function=	function3.apply(entityId, username, databaseName);
		
		Function2<String, UserPortrait, UserPortrait> function2 =
				(toAnnotate, bean) -> bean.setToAnnotate(toAnnotate);
		
		List<WriteModel<UserPortrait>> writeModelList =
				toAnnotates.stream()
						.map(function)
						.map(InsertOneModel::new)
						.collect(Collectors.toList());
		
		return kgmsFilePicture
				.bulkWrite(writeModelList, new BulkWriteOptions().ordered(true))
				.getInsertedCount();
	}
	
	public static void main(String[] args) {
		Long entityId=1L;
		String username="test";
		String databaseName="test2";
		List<String> toAnnotates= Arrays.asList("tes1","test2","tes4");
		Function4<Long, String, String,String, UserPortrait> function3 =
				(ei, un, db,annotate) -> new UserPortrait()
						.setEntityId(ei)
						.setUsername(un)
						.setDatabaseName(db)
						.setInsertTime(LocalDateTime.now())
						.setUpdateTime(LocalDateTime.now())
						.setToAnnotate(annotate);
		Function<String,UserPortrait> function=	function3.apply(entityId, username, databaseName);
		
		Function2<String, UserPortrait, UserPortrait> function2 =
				(toAnnotate, bean) -> bean.setToAnnotate(toAnnotate);
		toAnnotates.stream()
				.map(function)
				.forEach(System.out::println);
		
		Integer test=null;
		System.out.println(Optional.ofNullable(test).filter(Objects::nonNull).orElse(20));
	}
	
}

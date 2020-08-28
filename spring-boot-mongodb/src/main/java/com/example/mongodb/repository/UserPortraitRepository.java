package com.example.mongodb.repository;

import com.example.mongodb.entity.UserPortraitEntity;
import javax.annotation.Resource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UserPortraitRepository extends MongoRepository<UserPortraitEntity,String> {

}

package com.example.jpa.repository;

import com.example.jpa.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestEntityRepository extends CrudRepository<TestEntity, Long>,
		                                              JpaSpecificationExecutor<TestEntity>, JpaRepository<TestEntity, Long> {
}

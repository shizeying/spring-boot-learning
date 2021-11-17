package com.example.jpa.repository;

import com.example.jpa.entity.CategoryRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRuleRepository extends CrudRepository<CategoryRuleEntity, Long>,
		JpaSpecificationExecutor<CategoryRuleEntity>, JpaRepository<CategoryRuleEntity, Long> {
	
}

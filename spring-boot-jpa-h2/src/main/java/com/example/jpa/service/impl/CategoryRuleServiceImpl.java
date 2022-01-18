package com.example.jpa.service.impl;


import com.example.jpa.base.BaseEntity_;
import com.example.jpa.constant.PullDownEnum;
import com.example.jpa.constant.SortEnum;
import com.example.jpa.entity.CategoryRuleEntity;
import com.example.jpa.entity.CategoryRuleEntity_;
import com.example.jpa.model.dto.CategoryRuleDto;
import com.example.jpa.model.qo.CategoryRuleSearchQo;
import com.example.jpa.model.vo.CategoryRuleAddVo;
import com.example.jpa.repository.CategoryRuleRepository;
import com.example.jpa.service.CategoryRuleService;
import com.example.utils.config.JacksonUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CategoryRuleServiceImpl implements CategoryRuleService {
	
	@Autowired
	private CategoryRuleRepository categoryRuleRepository;
	@Autowired
	private EntityManager entityManager;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public CategoryRuleDto add(CategoryRuleAddVo data) {
		final CategoryRuleEntity entity = new CategoryRuleEntity();
		BeanUtils.copyProperties(data, entity);
		log.info("添加分类规则转换的对象：【{}】", JacksonUtil.bean2Json(entity));
		final CategoryRuleEntity save = categoryRuleRepository.saveAndFlush(entity);
		final CategoryRuleDto dto = new CategoryRuleDto();
		BeanUtils.copyProperties(save, dto, CategoryRuleDto.class);
		return dto;
		
		
	}
	
	@Override
	public Page<CategoryRuleEntity> page(CategoryRuleSearchQo data) {
		
		Specification<CategoryRuleEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
			
			//用于暂时存放查询条件的集合
			List<Predicate> predicatesList = Lists.newArrayList();
			boolean like = true;
			if (StringUtils.isAllBlank(
					data.getCategoryCode(),
					data.getProductionFactory(),
					data.getTypeName()
			)
					&&
					StringUtils.isNotBlank(data.getKw())) {
				String kw = "%" + data.getKw() + "%";
				//产品名称
				predicatesList.add(criteriaBuilder.like(root.get(CategoryRuleEntity_.productName), kw));
				//生产厂家
				predicatesList.add(
						criteriaBuilder.like(root.get(CategoryRuleEntity_.productModel), kw));
				//原型号
				predicatesList.add(
						criteriaBuilder.like(root.get(CategoryRuleEntity_.productionFactory), kw));
				//分类代码
				predicatesList.add(criteriaBuilder.like(root.get(CategoryRuleEntity_.categoryCode), kw));
				//类别名称
				predicatesList.add(criteriaBuilder.like(root.get(CategoryRuleEntity_.typeName3), kw));
				
			} else {
				like = false;
				if (StringUtils.isNotBlank(data.getCategoryCode())) {
					predicatesList.add(criteriaBuilder.equal(root.get(CategoryRuleEntity_.categoryCode),
							data.getCategoryCode()));
				}
				if (StringUtils.isNotBlank(data.getProductionFactory())) {
					predicatesList.add(criteriaBuilder.equal(root.get(CategoryRuleEntity_.productionFactory),
							data.getProductionFactory()));
				}
				if (StringUtils.isNotBlank(data.getTypeName())) {
					predicatesList.add(criteriaBuilder.equal(root.get(CategoryRuleEntity_.typeName3),
							data.getTypeName()));
				}
				
			}
			
			if (Objects.nonNull(data.getStartDate())) {
				predicatesList.add(
						criteriaBuilder.greaterThanOrEqualTo(root.get(BaseEntity_.updateTime),
								data.getStartDate()));
			}
			if (Objects.nonNull(data.getEndDate())) {
				predicatesList.add(
						criteriaBuilder.lessThanOrEqualTo(root.get(BaseEntity_.updateTime),
								data.getEndDate()));
			}
			Order updateTime;
			if (data.getSort().equals(SortEnum.asc)) {
				updateTime = criteriaBuilder.asc(root.get(BaseEntity_.updateTime));
			} else {
				updateTime = criteriaBuilder.desc(root.get(BaseEntity_.updateTime));
				
			}
			criteriaQuery.orderBy(updateTime,
					criteriaBuilder.asc(root.get(CategoryRuleEntity_.productName)),
					criteriaBuilder.asc(root.get(CategoryRuleEntity_.categoryCode)),
					criteriaBuilder.asc(root.get(CategoryRuleEntity_.productionFactory)),
					criteriaBuilder.asc(root.get(CategoryRuleEntity_.typeName3))
			
			);
			
			return like ? criteriaBuilder.or(predicatesList.toArray(new Predicate[0])) :
					criteriaBuilder.and(predicatesList.toArray(new Predicate[0]));
		};
		
		Pageable pageable = PageRequest.of(data.getPage() - 1, data.getPageSize());
		return categoryRuleRepository
				.findAll(specification, pageable);
		
		 
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteById(Long id) {
		if (Objects.nonNull(id)) {
			log.info("删除的规则id：【{}】", id);
			categoryRuleRepository.deleteById(id);
			log.info("删除一条替代规则成功....");
		}
	}
	
	@Override
	public List<String> pullDown(PullDownEnum type) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<String> cq = cb.createQuery(String.class);
		final Root<CategoryRuleEntity> root = cq.from(CategoryRuleEntity.class);
		cq.distinct(true);
		switch (type) {
			case categoryCode:
				cq.select(
						cb.construct(String.class, root.get(CategoryRuleEntity_.categoryCode)));
				break;
			case productionFactory:
				cq.select(
						cb.construct(String.class, root.get(CategoryRuleEntity_.productionFactory)));
				break;
			case typeName:
				
				
				cq.select(
						cb.construct(String.class, root.get(CategoryRuleEntity_.typeName3)));
				break;
			default:
				
				try {
					throw new NoSuchFieldException( "传入类型错误");
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
		}
		return entityManager.createQuery(cq)
				.getResultList();
	}
}

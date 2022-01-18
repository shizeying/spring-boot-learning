package com.example.jpa.entity;

import com.example.jpa.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = {"testEntityList"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "testEntityList")
//@RequiredArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Table(name = "kg_category_rule")
public class CategoryRuleEntity extends BaseEntity implements Serializable {
	
	/**
	 * 产品名称
	 */
	@NotEmpty(message = "产品名称不能为空")
	private String productName;
	/**
	 * 产品型号
	 */
	@NotEmpty(message = "产品型号不能为空")
	private String productModel;
	/**
	 * 生产厂家
	 */
	private String productionFactory;
	
	/**
	 * 分类代码
	 */
	private String categoryCode;
	/**
	 * 类别名称
	 */
	private String typeName3;
	
	
	@OneToMany(targetEntity = TestEntity.class, fetch = FetchType.EAGER)
	@JoinColumns(value = {
			@JoinColumn(name = "categoryRuleId", referencedColumnName = "id", insertable = false, nullable = false)
			//
	}, foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
	private List<TestEntity> testEntityList = new ArrayList<>();
	
	
}

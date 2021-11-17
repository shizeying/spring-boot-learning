package com.example.jpa.entity;

import com.example.jpa.base.BaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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
	private String typeName;
	
	
}

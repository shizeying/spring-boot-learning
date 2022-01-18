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

@EqualsAndHashCode(callSuper = false, exclude = {"categoryRuleEntity"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"categoryRuleEntity"})
//@RequiredArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Table(name = "kg_category_test")
public class TestEntity extends BaseEntity implements Serializable {
	
	/**
	 * 产品名称
	 */
	@Column(insertable = false, updatable = false)
	@NotEmpty(message = "产品名称不能为空")
	private Long categoryRuleId;
	
	private String typeName;
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH})
	@JoinColumn(name = "categoryRuleId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
	private CategoryRuleEntity categoryRuleEntity;
	
}

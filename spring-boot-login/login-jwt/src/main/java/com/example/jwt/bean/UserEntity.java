package com.example.jwt.bean;


import com.example.jwt.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@ToString
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Data
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames="username")})
@EntityListeners({AuditingEntityListener.class})
public class UserEntity extends BaseEntity {

  /**
   *
   */

  @Column(unique=true)
  private String username;

  private String nickname;

  private String password;
  private String seed;
  @Column(nullable = false, columnDefinition = "int default 0")
  private int iDrift;
  @Column(nullable = false, columnDefinition = "int default 0")
  private long lSucc = 0;




}

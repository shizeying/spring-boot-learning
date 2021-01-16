package com.example.domain.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity {
	@ApiModelProperty("主键")
	@TableId(type = IdType.AUTO)
	private Long id;
	@TableField(value = "create_at")
	@ApiModelProperty("创建时间")
	private LocalDateTime createAt;
	@ApiModelProperty("更新时间")
	@TableField(value = "update_at")
	private LocalDateTime updateAt;
}

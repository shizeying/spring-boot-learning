package com.example.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author hjh
 */
@Data
@ApiModel
@Accessors(chain = true)
public class GraphConfSnapshotGroup implements Serializable {
	
	private static final long serialVersionUID = 6163369825048118489L;
	/***
	 * 分组id
	 */
	@ApiModelProperty("分组收藏夹id")
	@TableId(value = "id", type = IdType.AUTO)
	
	private Long id;
	/**
	 * 收藏夹名称
	 */
	@NotBlank
	@ApiModelProperty("分组收藏夹名称")
	private String subjectName;
	/**
	 * 快照下子快照
	 */
	@ApiModelProperty("分组收藏夹下子快照id")
	private Long graphConfSnapshotId;
	@ApiModelProperty("焦点实体")
	private String focusEntityName;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("创建时间")
	private LocalDateTime createDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("更新时间")
	private LocalDateTime updateDate;
}

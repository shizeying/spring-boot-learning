package com.example.mybatis.plus.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author hjh
 */
@Data
@ApiModel
@Accessors(chain = true)
@SuppressWarnings("ALL")
public class SnapshotGroupBO extends SnapshotGroup implements Serializable {
	
	
	@ApiModelProperty("单页应用id")
	private String spaId;
	@ApiModelProperty("快照配置")
	private String snapshotConfig;
	@ApiModelProperty("创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String createAt;
	@ApiModelProperty("更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String updateAt;
	@ApiModelProperty("描述")
	private String remark;
	@ApiModelProperty("快照名称")
	private String name;
	
}

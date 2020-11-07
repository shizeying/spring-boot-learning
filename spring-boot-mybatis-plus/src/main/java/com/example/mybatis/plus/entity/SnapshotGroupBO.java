package com.example.mybatis.plus.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
	@ApiModelProperty("配置")
	private JSONObject snapshotConfig;
	@ApiModelProperty("创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String createAt;
	@ApiModelProperty("时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String updateAt;
	@ApiModelProperty("描述")
	private String remark;
	@ApiModelProperty("快照名称")
	private String name;
	public void setSnapshotConfig(String snapshotConfig) {
		this.snapshotConfig = JSON.parseObject(snapshotConfig);
	}
	
	
}

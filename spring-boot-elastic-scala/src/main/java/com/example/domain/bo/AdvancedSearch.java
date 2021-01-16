package com.example.domain.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 高级搜索
 *
 * @author shizeying
 * @date 2021/01/01
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdvancedSearch  implements Serializable {
	private Object detail = new Object();
	private String type = "0";
	
	
}

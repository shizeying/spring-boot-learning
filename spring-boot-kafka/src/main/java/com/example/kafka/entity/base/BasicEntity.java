package com.example.kafka.entity.base;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 基本的实体
 *
 * @author shizeying
 * @date 2020/09/03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BasicEntity implements Serializable {

    /**
     * 关联Id
     */
    private String reId;

}

package com.example.hive2es.entity.vo.event.relation;

import com.example.hive2es.entity.common.BasicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TInfoHotelRecordRelation extends BasicEntity {

    /**
     * 关系开始实体：登记人
     */
    private String relBeganToEntities;
    /**
     * 订票手机号
     */
    private String bookingMobile;

    /**
     * 居住酒店
     */
    private String livingInHotels;
}

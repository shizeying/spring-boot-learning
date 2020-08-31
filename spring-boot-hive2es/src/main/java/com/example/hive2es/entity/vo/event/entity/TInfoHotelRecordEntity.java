package com.example.hive2es.entity.vo.event.entity;

import com.example.hive2es.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TInfoHotelRecordEntity extends BaseEntity {

    /**
     * 房间号
     */
    private String roomNumber;
    /**
     * 总金额
     */
    private Double amount;
    /**
     * 房型
     */
    private String room;
    /**
     * 预计到店时间
     */
    private String expectedTimeToShop;
    /**
     * 预计离店时间
     */
    private String antDepartureTime;
}

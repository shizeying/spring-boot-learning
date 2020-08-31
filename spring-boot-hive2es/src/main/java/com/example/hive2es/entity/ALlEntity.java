package com.example.hive2es.entity;

import com.example.hive2es.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ALlEntity extends BaseEntity {


  //TODO =====================================TIntelligencePersonEntity=============================
  private String gender;
  private Integer height;
  private Integer age;
  private String position;
  private String englishName;
  private String winning;
  private String familyName;
  private String givenName;
  private String deathDate;
  private String bloodType;
  private String constellation;
  private Double weight;
  private String country;
  private String national;
  private String degree;
  private String meaningTag;
  private String accomplishment;
  private String expertise;
  private String profession;
  private String feature;
  private String religion;
  private String url;
  private String test;

  //TODO ========================================TInfoHotelRecordEntity==================================

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
  //TODO ===========================================TInfoHotelRecordRelation==================================
  private String relBeganToEntities;
  private String bookingMobile;
  private String livingInHotels;

}

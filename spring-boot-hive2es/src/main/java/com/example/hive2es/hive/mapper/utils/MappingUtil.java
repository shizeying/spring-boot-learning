package com.example.hive2es.hive.mapper.utils;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.Optional;

/**
 * 对应的映射，可以将全部字段取到然后放入,然后再SourceJavaBeanMapper接口中对应方法填写就可以了
 * 对应的表按照以下方式进行标注
 *
 * @author shizeying
 * @date 2020/09/02
 */
public class MappingUtil {


    //TODO ===================================test=========================
    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Test {
    }

    @Test
    public String test(Map<String, Object> in) {
        return (String) in.get("test");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface CreatedAt {
    }

    @CreatedAt
    public Object createdAt(Map<String, Object> in) {
        return in.get("createdAt");
    }
    //TODO ===================================common========================

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Name {
    }

    @Name
    public String name(Map<String, Object> in) {
        return (String) in.get("name");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Abs {
    }

    @Abs
    public String abs(Map<String, Object> in) {
        return (String) in.get("abs");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Origin {
    }

    @Origin
    public String origin(Map<String, Object> in) {
        return (String) in.get("origin");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Image {
    }

    @Image
    public String image(Map<String, Object> in) {
        return (String) in.get("image");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface IdentifyTag {
    }

    @IdentifyTag
    public String identifyTag(Map<String, Object> in) {
        return (String) in.get("identifyTag");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Lon {
    }

    @Lon
    public Double lon(Map<String, Object> in) {
        return Double.parseDouble(
                Optional.ofNullable(String.valueOf(Optional.ofNullable(in.get("lon")).orElse(0))).get());
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Lat {
    }

    @Lat
    public Double lat(Map<String, Object> in) {
        return Double.parseDouble(
                Optional.ofNullable(String.valueOf(Optional.ofNullable(in.get("lat")).orElse(0))).get());
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface GisAdress {
    }

    @GisAdress
    public String gisAdress(Map<String, Object> in) {
        return (String) in.get("gisAdress");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface StartTime {
    }

    @StartTime
    public String startTime(Map<String, Object> in) {
        return (String) in.get("startTime");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface EndTime {
    }

    @EndTime
    public String endTime(Map<String, Object> in) {
        return (String) in.get("endTime");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface EntityTag {
    }

    @EntityTag
    public String entityTag(Map<String, Object> in) {
        return (String) in.get("entityTag");
    }


    //TODO =============================================TIntelligencePersonEntity================

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Gender {
    }

    @Gender
    public String gender(Map<String, Object> in) {
        return (String) in.get("gender");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Height {
    }

    @Height
    public Integer height(Map<String, Object> in) {
        return Integer.parseInt(
                Optional.ofNullable(String.valueOf(Optional.ofNullable(in.get("height")).orElse(0)))
                        .get());
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Age {
    }

    @Age
    public Integer age(Map<String, Object> in) {
        return Integer.parseInt(
                Optional.ofNullable(String.valueOf(Optional.ofNullable(in.get("height")).orElse(0)))
                        .get());
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Position {
    }

    @Position
    public String position(Map<String, Object> in) {
        return (String) in.get("position");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Englishname {
    }

    @Englishname
    public String englishname(Map<String, Object> in) {
        return (String) in.get("englishname");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Winning {
    }

    @Winning
    public String winning(Map<String, Object> in) {
        return (String) in.get("winning");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Familyname {
    }

    @Familyname
    public String familyname(Map<String, Object> in) {
        return (String) in.get("familyname");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Givenname {
    }

    @Givenname
    public String givenname(Map<String, Object> in) {
        return (String) in.get("givenname");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Deathdate {
    }

    @Deathdate
    public String deathdate(Map<String, Object> in) {
        return (String) in.get("deathdate");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Bloodtype {
    }

    @Bloodtype
    public String bloodtype(Map<String, Object> in) {
        return (String) in.get("bloodtype");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Constellation {
    }

    @Constellation
    public String constellation(Map<String, Object> in) {
        return (String) in.get("constellation");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Weight {
    }

    @Weight
    public Double weight(Map<String, Object> in) {

        return Double.parseDouble(
                Optional.ofNullable(String.valueOf(Optional.ofNullable(in.get("weight")).orElse(0))).get());
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Country {
    }

    @Country
    public String country(Map<String, Object> in) {
        return (String) in.get("country");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface National {
    }

    @National
    public String national(Map<String, Object> in) {
        return (String) in.get("national");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Degree {
    }

    @Degree
    public String degree(Map<String, Object> in) {
        return (String) in.get("degree");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Meaningtag {
    }

    @Meaningtag
    public String meaningtag(Map<String, Object> in) {
        return (String) in.get("meaningtag");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Accomplishment {
    }

    @Accomplishment
    public String accomplishment(Map<String, Object> in) {
        return (String) in.get("accomplishment");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Expertise {
    }

    @Expertise
    public String expertise(Map<String, Object> in) {
        return (String) in.get("expertise");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Profession {
    }

    @Profession
    public String profession(Map<String, Object> in) {
        return (String) in.get("profession");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Feature {
    }

    @Feature
    public String feature(Map<String, Object> in) {
        return (String) in.get("feature");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Religion {
    }

    @Religion
    public String religion(Map<String, Object> in) {
        return (String) in.get("religion");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Url {
    }

    @Url
    public String url(Map<String, Object> in) {
        return (String) in.get("url");
    }

    //TODO =================================================TInfoHotelRecordEntity==============================

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface RoomNumber {
    }

    @RoomNumber
    public String roomNumber(Map<String, Object> in) {
        return (String) in.get("roomNumber");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Amount {
    }

    @Amount
    public Double amount(Map<String, Object> in) {


        return Double.parseDouble(
                Optional.ofNullable(String.valueOf(Optional.ofNullable(in.get("amount")).orElse(0))).get());
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Room {
    }

    @Room
    public String room(Map<String, Object> in) {
        return (String) in.get("room");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface ExpectedTimeToShop {
    }

    @ExpectedTimeToShop
    public String expectedTimeToShop(Map<String, Object> in) {
        return (String) in.get("expectedTimeToShop");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface AntDepartureTime {
    }

    @AntDepartureTime
    public String antDepartureTime(Map<String, Object> in) {
        return (String) in.get("antDepartureTime");
    }


    //TODO ==============================================TInfoHotelRecordRelation====================

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface RelBeganToEntities {
    }

    @RelBeganToEntities
    public String elBeganToEntities(Map<String, Object> in) {
        return (String) in.get("elBeganToEntities");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface BookingMobile {
    }

    @BookingMobile
    public String bookingMobile(Map<String, Object> in) {
        return (String) in.get("bookingMobile");
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface LivingInHotels {
    }

    @LivingInHotels
    public String livingInHotels(Map<String, Object> in) {
        return (String) in.get("livingInHotels");
    }


}

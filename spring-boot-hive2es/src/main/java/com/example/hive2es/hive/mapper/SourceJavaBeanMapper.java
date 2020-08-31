package com.example.hive2es.hive.mapper;

import com.example.hive2es.entity.ALlEntity;
import com.example.hive2es.entity.dto.Source;
import com.example.hive2es.entity.vo.TestEntity;
import com.example.hive2es.entity.vo.event.entity.TInfoHotelRecordEntity;
import com.example.hive2es.entity.vo.entityType.entity.TIntelligencePersonEntity;
import com.example.hive2es.entity.vo.event.relation.TInfoHotelRecordRelation;
import com.example.hive2es.hive.mapper.utils.MappingUtil;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 指定map对应javabean中的字段,ALL这个放全部类，通过反射获取对应的javabean
 *
 * @author shizeying
 * @date 2020/09/02
 */
@Mapper(uses = MappingUtil.class)
public interface SourceJavaBeanMapper {
    SourceJavaBeanMapper MAPPER = Mappers.getMapper(SourceJavaBeanMapper.class);

    // TODO ========================test===========================
    @Mapping(source = "map", target = "test", qualifiedBy = MappingUtil.Test.class)
    // TODO ========================common=========================
    @Mapping(source = "map", target = "name", qualifiedBy = MappingUtil.Name.class)
    @Mapping(source = "map", target = "abs", qualifiedBy = MappingUtil.Abs.class)
    @Mapping(source = "map", target = "origin", qualifiedBy = MappingUtil.Origin.class)
    @Mapping(source = "map", target = "image", qualifiedBy = MappingUtil.Image.class)
    @Mapping(source = "map", target = "identifyTag", qualifiedBy = MappingUtil.IdentifyTag.class)
    @Mapping(source = "map", target = "lon", qualifiedBy = MappingUtil.Lon.class)
    @Mapping(source = "map", target = "lat", qualifiedBy = MappingUtil.Lat.class)
    @Mapping(source = "map", target = "gisAdress", qualifiedBy = MappingUtil.GisAdress.class)
    @Mapping(source = "map", target = "startTime", qualifiedBy = MappingUtil.StartTime.class)
    @Mapping(source = "map", target = "endTime", qualifiedBy = MappingUtil.EndTime.class)
    @Mapping(source = "map", target = "entityTag", qualifiedBy = MappingUtil.EntityTag.class)
    // TODO =========================TIntelligencePersonEntity==========
    @Mapping(source = "map", target = "gender", qualifiedBy = MappingUtil.Gender.class)
    @Mapping(source = "map", target = "height", qualifiedBy = MappingUtil.Height.class)
    @Mapping(source = "map", target = "age", qualifiedBy = MappingUtil.Age.class)
    @Mapping(source = "map", target = "position", qualifiedBy = MappingUtil.Position.class)
    @Mapping(source = "map", target = "englishName", qualifiedBy = MappingUtil.Englishname.class)
    @Mapping(source = "map", target = "winning", qualifiedBy = MappingUtil.Winning.class)
    @Mapping(source = "map", target = "familyName", qualifiedBy = MappingUtil.Familyname.class)
    @Mapping(source = "map", target = "givenName", qualifiedBy = MappingUtil.Givenname.class)
    @Mapping(source = "map", target = "deathDate", qualifiedBy = MappingUtil.Deathdate.class)
    @Mapping(source = "map", target = "bloodType", qualifiedBy = MappingUtil.Bloodtype.class)
    @Mapping(source = "map", target = "constellation", qualifiedBy = MappingUtil.Constellation.class)
    @Mapping(source = "map", target = "weight", qualifiedBy = MappingUtil.Weight.class)
    @Mapping(source = "map", target = "country", qualifiedBy = MappingUtil.Country.class)
    @Mapping(source = "map", target = "national", qualifiedBy = MappingUtil.National.class)
    @Mapping(source = "map", target = "degree", qualifiedBy = MappingUtil.Degree.class)
    @Mapping(source = "map", target = "meaningTag", qualifiedBy = MappingUtil.Meaningtag.class)
    @Mapping(
            source = "map",
            target = "accomplishment",
            qualifiedBy = MappingUtil.Accomplishment.class)
    @Mapping(source = "map", target = "expertise", qualifiedBy = MappingUtil.Expertise.class)
    @Mapping(source = "map", target = "profession", qualifiedBy = MappingUtil.Profession.class)
    @Mapping(source = "map", target = "feature", qualifiedBy = MappingUtil.Feature.class)
    @Mapping(source = "map", target = "religion", qualifiedBy = MappingUtil.Religion.class)
    @Mapping(source = "map", target = "url", qualifiedBy = MappingUtil.Url.class)
    //TODO=====================================TInfoHotelRecordEntity=================
    @Mapping(source = "map", target = "roomNumber", qualifiedBy = MappingUtil.RoomNumber.class)
    @Mapping(source = "map", target = "amount", qualifiedBy = MappingUtil.Amount.class)
    @Mapping(source = "map", target = "room", qualifiedBy = MappingUtil.Url.class)
    @Mapping(source = "map", target = "expectedTimeToShop", qualifiedBy = MappingUtil.ExpectedTimeToShop.class)
    @Mapping(source = "map", target = "antDepartureTime", qualifiedBy = MappingUtil.AntDepartureTime.class)
    //TODO ====================================TInfoHotelRecordRelation==================
    @Mapping(source = "map", target = "relBeganToEntities", qualifiedBy = MappingUtil.RelBeganToEntities.class)
    @Mapping(source = "map", target = "bookingMobile", qualifiedBy = MappingUtil.BookingMobile.class)
    @Mapping(source = "map", target = "livingInHotels", qualifiedBy = MappingUtil.LivingInHotels.class)
    ALlEntity toJavaBean(Source source);

    //TODO ----------------------------------TestEntity
    // TODO 这是一个实例，通过反射批量转换
    @InheritInverseConfiguration(name = "aLlEntity")
    TestEntity VOToTestEntity(ALlEntity aLlEntity);

    /**
     * 集合的批量转换,会调用
     */
    List<TestEntity> entitiesToTestVOs(List<ALlEntity> entities);

    //TODO ----------------------------------TIntelligencePersonEntity

    @InheritInverseConfiguration(name = "aLlEntity")
    TIntelligencePersonEntity VOToTPersonEntity(ALlEntity aLlEntity);

    List<TIntelligencePersonEntity> entitiesToVOToTPersonEntity(List<ALlEntity> entities);

    //TODO ----------------------------------TInfoHotelRecordEntity

    @InheritInverseConfiguration(name = "aLlEntity")
    TInfoHotelRecordEntity VOToTInfoHotelRecordEntity(ALlEntity aLlEntity);

    List<TInfoHotelRecordEntity> entitiesToVOToTInfoHotelRecordEntity(List<ALlEntity> entities);

    //TODO ----------------------------------TInfoHotelRecordRelation

    @InheritInverseConfiguration(name = "aLlEntity")
    TInfoHotelRecordRelation VOToTInfoHotelRecordRelation(ALlEntity aLlEntity);

    List<TInfoHotelRecordRelation> entitiesToVOToTInfoHotelRecordRelation(List<ALlEntity> entities);


}

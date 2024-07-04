package org.example.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Business;

import java.util.List;

@Mapper
public interface BusinessMapper {
    @Select("select * from business")
    List<Business> list();
    @Select("select * from business where businessName like concat('%',#{name},'%')")
    List<Business> search(String name);

    @Select("select * from business where orderTypeId = #{orderTypeId}")
    List<Business> getByOrderTypeId(Integer orderTypeId);

    @Select("select * from business where businessId=#{businessId}")
    Business getByBusinessId(Integer businessId);

    @Insert("insert into business" +
            "(businessName, businessAddress, businessExplain, businessImg, orderTypeId, starPrice, deliveryPrice, remarks)" +
            "values " +
            "(#{businessName}, #{businessAddress}, #{businessExplain},#{businessImg},#{orderTypeId},#{starPrice},#{deliveryPrice},#{remarks})")
    void add(Business business);

    @Delete("delete from business where businessId = #{businessId}")
    void deleteBusiness(Integer businessId);

    @Delete("delete from food where businessId = #{businessId}")
    void deleteFood(Integer businessId);


}

package org.example.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Food;

import java.util.List;

@Mapper
public interface FoodMapper {

    @Select("select * from food where foodId=#{foodId}")
    Food getFoodById(Integer foodId);

    @Select("select * from food where businessId=#{businessId}")
    List<Food> listFoodByBusiness(Integer businessId);


    @Insert("insert into food(foodName, foodExplain, foodImg, foodPrice, businessId, remarks)" +
            "values (#{foodName}, #{foodExplain}, #{foodImg}, #{foodPrice}, #{businessId}, #{remarks})")
    void addFood(Food food);


    @Delete("delete from food where foodId=#{foodId}")
    void deleteFood(Integer foodId);
}

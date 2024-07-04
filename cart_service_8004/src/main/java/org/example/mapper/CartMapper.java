package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Cart;

import java.util.List;

@Mapper
public interface CartMapper {

    @Select("select * from cart where foodId=#{foodId} and userId = #{userId}")
    Cart getCard(@Param("foodId") Integer foodId, @Param("userId") Integer userId);
    @Insert("insert into cart(foodId, businessId, userId, quantity)" +
            "values(#{foodId}, #{businessId}, #{userId}, #{quantity})")
    void addCart(Cart cart);

    @Update("update cart set quantity = 0 where cartId = #{cartId}")
    void updateCartQuantity(Integer cartId);

    @Update("update cart set quantity = quantity + 1 " +
            "where cartId=#{cartId}")
    void addQuantity(Integer cartId);

    @Update("update cart set quantity = quantity - 1 " +
            "where cartId=#{cartId}")
    void substrateQuantity(Integer cartId);

    @Select("select quantity from cart where cartId = #{cartId}")
    Integer getCartQuantity(Integer cartId);

    @Update("update cart set quantity = 0 " +
            "where cartId=#{cartId}")
    void setQuantity(Integer cartId);

    @Select("select * from cart where businessId=#{businessId} and userId = #{userId} and quantity != 0")
    List<Cart> getCartByBusiness(@Param("businessId") Integer businessId, @Param("userId") Integer userId);
}

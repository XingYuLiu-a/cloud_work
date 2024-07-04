package org.example.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.OrderDetailet;
import org.example.pojo.Orders;

import java.util.List;

@Mapper
public interface  OrdersMapper {
    void addOrders(Orders orders);
    @Insert("insert into  orderdetailet(orderId, foodId, quantity) " +
            "values (#{orderId},#{foodId},#{quantity})")
   void addOrderDetailet(OrderDetailet orderDetailet);
    @Select("select * from orderdetailet where orderId = #{orderId}")
    List<OrderDetailet> getOrderDetailets(Integer orderId);
    @Select("select count(*) from orders where userId = #{userId} and businessId = #{businessId} and orderState = 0")
    Integer check(Orders orders);

    @Select("select businessId from orders where orderId=#{orderId}")
    Integer getBusinessId(Integer orderId);

    @Select("select * from orders where orderId=#{orderId}")
    Orders getOrderByOrderId(Integer orderId);
    void updateOrders(Orders orders);
    @Delete("delete from orders where orderId = #{orderId}")
    void deleteOrders(Integer orderId);
    @Delete("delete from orderdetailet where orderId = #{orderId}")
    void deleteOrderDetails(Integer orderId);
    @Select("select * from orders where userId=#{userId} and orderState = 0")
    List<Orders> getOrdersNotPay(Integer userId);

    @Select("select * from orders where userId=#{userId} and orderState = 1")
    List<Orders> getOrdersPay(Integer userId);
}

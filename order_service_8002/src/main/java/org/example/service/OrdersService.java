package org.example.service;



import org.example.pojo.Business;
import org.example.pojo.OrderDetailet;
import org.example.pojo.Orders;

import java.util.List;

public interface OrdersService {
    Integer addOrders(Orders orders);

    List<OrderDetailet> getOrderDetailets(Integer orderId);

    Business getBusinessByOrderId(Integer orderId);

    Orders getOrderByOrderId(Integer orderId);

    void updateOrders(Orders orders);

    void deleteOrders(Integer orderId);

    List<Orders> getOrdersNotPay(Integer userId);
    List<Orders> getOrdersPay(Integer userId);
}

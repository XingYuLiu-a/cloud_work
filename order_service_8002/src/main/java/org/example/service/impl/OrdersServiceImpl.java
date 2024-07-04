package org.example.service.impl;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import org.example.apis.BusinessApi;
import org.example.apis.CartApi;
import org.example.apis.DeliveryAddressApi;
import org.example.apis.FoodApi;
import org.example.mapper.OrdersMapper;
import org.example.pojo.*;
import org.example.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    private static final Logger logger = LoggerFactory.getLogger(OrdersServiceImpl.class);
    @Autowired
    private OrdersMapper ordersMapper;
    @Resource
    private CartApi cartApi;
    @Resource
    private FoodApi foodApi;
    @Resource
    private DeliveryAddressApi deliveryAddressApi;
    @Resource
    private BusinessApi businessApi;
    private static final String ORDERS_SERVICE = "orders-service";
    @Override
    @CircuitBreaker(name = ORDERS_SERVICE, fallbackMethod = "defaultAddOrders")
    public Integer addOrders(Orders orders) {
        Integer count = ordersMapper.check(orders);
        if (count == 0){
            LocalDateTime dateTime = LocalDateTime.now();
            String date=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime);
            orders.setOrderDate(date);
            orders.setOrderState(0);
            ordersMapper.addOrders(orders);
            List<Cart> cartList = cartApi.getCartByBusiness(orders.getBusinessId(),orders.getUserId());
            for(Cart cart :cartList){
                OrderDetailet orderDetailet = new OrderDetailet();
                orderDetailet.setOrderId(orders.getOrderId());
                orderDetailet.setQuantity(cart.getQuantity());
                orderDetailet.setFoodId(cart.getFoodId());
                ordersMapper.addOrderDetailet(orderDetailet);
                cartApi.updateQuantity(cart.getCartId());
            }
            return orders.getOrderId();
        } else {
            return -1;
        }
    }

    @Override
    @CircuitBreaker(name = ORDERS_SERVICE, fallbackMethod = "defaultGetOrderDetailets")
    public List<OrderDetailet> getOrderDetailets(Integer orderId) {
        List<OrderDetailet> orderDetailetList = ordersMapper.getOrderDetailets(orderId);
        for(OrderDetailet orderDetailet : orderDetailetList){
            Food food = foodApi.getById(orderDetailet.getFoodId());
            orderDetailet.setFood(food);
        }
        return orderDetailetList;
    }

    @Override
    @CircuitBreaker(name = ORDERS_SERVICE, fallbackMethod = "defaultGetBusinessByOrderId")
    public Business getBusinessByOrderId(Integer orderId) {
        Business business = businessApi.getById(ordersMapper.getBusinessId(orderId));
        return business;
    }

    @Override
    @CircuitBreaker(name = ORDERS_SERVICE, fallbackMethod = "defaultGetOrderByOrderId")
    public Orders getOrderByOrderId(Integer orderId) {
        Orders orders = ordersMapper.getOrderByOrderId(orderId);
        DeliveryAddress deliveryAddress = deliveryAddressApi.getById(orders.getDaId());
        orders.setDeliveryAddress(deliveryAddress);
        return orders;
    }

    @Override
    @CircuitBreaker(name = ORDERS_SERVICE, fallbackMethod = "defaultUpdateOrders")
    public void updateOrders(Orders orders) {
        ordersMapper.updateOrders(orders);
    }

    @Override
    @CircuitBreaker(name = ORDERS_SERVICE, fallbackMethod = "defaultDeleteOrders")
    public void deleteOrders(Integer orderId) {
        ordersMapper.deleteOrders(orderId);
        ordersMapper.deleteOrderDetails(orderId);
    }

    @Override
    @CircuitBreaker(name = ORDERS_SERVICE, fallbackMethod = "defaultGetOrdersNotPay")
    public List<Orders> getOrdersNotPay(Integer userId) {
        List<Orders> ordersList = ordersMapper.getOrdersNotPay(userId);
        for(Orders orders : ordersList){
            Business business = businessApi.getById(orders.getBusinessId());
            orders.setBusiness(business);
            List<OrderDetailet> orderDetailetList = ordersMapper.getOrderDetailets(orders.getOrderId());
            for(OrderDetailet orderDetailet : orderDetailetList){
                Food food = foodApi.getById(orderDetailet.getFoodId());
                orderDetailet.setFood(food);
            }
            orders.setOrderDetailetList(orderDetailetList);
        }
        return ordersList;
    }

    @Override
    @CircuitBreaker(name = ORDERS_SERVICE, fallbackMethod = "defaultGetOrdersPay")
    public List<Orders> getOrdersPay(Integer userId) {
        List<Orders> ordersList = ordersMapper.getOrdersPay(userId);
        for(Orders orders : ordersList){
            Business business = businessApi.getById(orders.getBusinessId());
            orders.setBusiness(business);
            List<OrderDetailet> orderDetailetList = ordersMapper.getOrderDetailets(orders.getOrderId());
            for(OrderDetailet orderDetailet : orderDetailetList){
                Food food = foodApi.getById(orderDetailet.getFoodId());
                orderDetailet.setFood(food);
            }
            orders.setOrderDetailetList(orderDetailetList);
        }
        return ordersList;
    }

    // Fallback methods

    public Integer defaultAddOrders(Orders orders, Throwable throwable) {
        logger.error("Error in addOrders method, orders: {}, error: {}", orders, throwable.getMessage());
        return -999;
    }

    public List<OrderDetailet> defaultGetOrderDetailets(Integer orderId, Throwable throwable) {
        logger.error("Error in getOrderDetailets method, orderId: {}, error: {}", orderId, throwable.getMessage());

        return null;
    }

    public Business defaultGetBusinessByOrderId(Integer orderId, Throwable throwable) {
        logger.error("Error in getBusinessByOrderId method, orderId: {}, error: {}", orderId, throwable.getMessage());

        return null;
    }

    public Orders defaultGetOrderByOrderId(Integer orderId, Throwable throwable) {
        logger.error("Error in getOrderByOrderId method, orderId: {}, error: {}", orderId, throwable.getMessage());

        return null;
    }

    public void defaultUpdateOrders(Orders orders, Throwable throwable) {
        logger.error("Error in updateOrders method, orders: {}, error: {}", orders, throwable.getMessage());
    }

    public void defaultDeleteOrders(Integer orderId, Throwable throwable) {
        logger.error("Error in deleteOrders method, orderId: {}, error: {}", orderId, throwable.getMessage());
    }

    public List<Orders> defaultGetOrdersNotPay(Integer userId, Throwable throwable) {
        logger.error("Error in getOrdersNotPay method, userId: {}, error: {}", userId, throwable.getMessage());

        return null;
    }

    public List<Orders> defaultGetOrdersPay(Integer userId, Throwable throwable) {
        logger.error("Error in getOrdersPay method, userId: {}, error: {}", userId, throwable.getMessage());

        return null;
    }
}

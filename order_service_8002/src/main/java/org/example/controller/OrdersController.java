package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Business;
import org.example.pojo.OrderDetailet;
import org.example.pojo.Orders;
import org.example.resp.ResultData;
import org.example.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;


    @PostMapping("/")
    public ResultData addOrders(@RequestBody Orders orders){
        Integer orderId = ordersService.addOrders(orders);
        if (orderId == -1){
            return ResultData.fail(999,"这个商家还有一个订单没有支付,请先支付上一个订单!");
        }else {
            return ResultData.success(orderId);
        }
    }

    @GetMapping("/orderDetailet/{orderId}")
    public ResultData getOrderDetailets(@PathVariable("orderId") Integer orderId){
        List<OrderDetailet> orderDetailetList = ordersService.getOrderDetailets(orderId);
        return ResultData.success(orderDetailetList);
    }

    @GetMapping("/businessInfo/{orderId}")
    public ResultData getBusinessByOrderId(@PathVariable("orderId") Integer orderId){
        Business business = ordersService.getBusinessByOrderId(orderId);

        return ResultData.success(business);
    }

    @GetMapping("/{orderId}")
    public ResultData getOrderByOrderId(@PathVariable("orderId") Integer orderId){
        Orders orders = ordersService.getOrderByOrderId(orderId);

        return ResultData.success(orders);
    }

    @PutMapping("/")
    public ResultData updateOrders(@RequestBody Orders orders){
        ordersService.updateOrders(orders);
        return ResultData.success(null);
    }

    @DeleteMapping("/{orderId}")
    public ResultData deleteOrders(@PathVariable("orderId") Integer orderId){
        ordersService.deleteOrders(orderId);
        return ResultData.success(null);
    }

    @GetMapping("/orderListNotPay/{userId}")
    public ResultData getOrdersByUserIdNotPay(@PathVariable("userId") Integer userId){
        List<Orders> ordersList = ordersService.getOrdersNotPay(userId);

        return ResultData.success(ordersList);
    }
    @GetMapping("/orderListPay/{userId}")
    public ResultData getOrdersByUserIdPay(@PathVariable("userId") Integer userId){
        List<Orders> ordersList = ordersService.getOrdersPay(userId);

        return ResultData.success(ordersList);
    }
}

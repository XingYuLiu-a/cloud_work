package org.example.service.impl;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import org.example.apis.CartApi;
import org.example.apis.FoodApi;
import org.example.mapper.BusinessMapper;
import org.example.pojo.Business;
import org.example.pojo.Cart;
import org.example.pojo.Food;
import org.example.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {
    private static final Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);
    @Autowired
    private BusinessMapper businessMapper;

    @Resource
    private CartApi cartApi;
    @Resource
    private FoodApi foodApi;

    @Override
    @CircuitBreaker(name = "business-service", fallbackMethod = "defaultList")
    public List<Business> list() {
        return businessMapper.list();
    }

    @Override
    @CircuitBreaker(name = "business-service", fallbackMethod = "defaultList")
    public List<Business> search(String name){
        return businessMapper.search(name);
    }

    @Override
    @CircuitBreaker(name = "business-service", fallbackMethod = "defaultList")
    public List<Business> getByOrderTypeId(Integer orderTypeId) {
        return businessMapper.getByOrderTypeId(orderTypeId);
    }

    @Override
    @CircuitBreaker(name = "business-service", fallbackMethod = "defaultAdd")
    public void add(Business business) {
        businessMapper.add(business);
    }

    @Override
    @CircuitBreaker(name = "business-service", fallbackMethod = "defaultDelete")
    public void delete(Integer businessId) {
        businessMapper.deleteBusiness(businessId);
        businessMapper.deleteFood(businessId);
    }

    @Override
    @CircuitBreaker(name = "business-service", fallbackMethod = "defaultBusiness")
    public Business getByBusinessId(Integer businessId) {
        return businessMapper.getByBusinessId(businessId);
    }

    @Override
    @CircuitBreaker(name = "business-service", fallbackMethod = "defaultTotal")
    public Double total(Integer businessId, Integer userId) {
        Double total = 0.0;
        List<Cart> cartList = cartApi.getCartByBusiness(businessId, userId);
        for(Cart cart : cartList){
            Food food = foodApi.getById(cart.getFoodId());
            Double price =  food.getFoodPrice();
            total+=price*cart.getQuantity();
        }
        Double deliveryPrice = businessMapper.getByBusinessId(businessId).getDeliveryPrice();
        total+=deliveryPrice;
        return total;
    }

    @Override
    @CircuitBreaker(name = "business-service", fallbackMethod = "defaultQuantity")
    public Integer getQuantity(Integer businessId, Integer userId) {
        Integer quantity = 0;
        List<Cart> cartList = cartApi.getCartByBusiness(businessId, userId);
        for(Cart cart : cartList){
            quantity+=cart.getQuantity();
        }
        return quantity;
    }

    // Fallback methods
    public List<Business> defaultList(Throwable throwable) {
        logger.error("Error in list method, error: {}", throwable.getMessage());

        return null;
    }
    public void defaultAdd(Business business, Throwable throwable) {
        logger.error("Error in add method, business: {}, error: {}", business, throwable.getMessage());
    }
    public void defaultDelete(Integer businessId, Throwable throwable) {
        logger.error("Error in delete method, businessId: {}, error: {}", businessId, throwable.getMessage());
    }
    public Business defaultBusiness(Integer businessId, Throwable throwable) {
        logger.error("Error in getByBusinessId method, businessId: {}, error: {}", businessId, throwable.getMessage());

        return null;
    }

    public Double defaultTotal(Integer businessId, Integer userId, Throwable throwable) {
        logger.error("Error in total method, businessId: {}, userId: {}, error: {}", businessId, userId, throwable.getMessage());
        return -1.0;
    }

    public Integer defaultQuantity(Integer businessId, Integer userId, Throwable throwable) {
        logger.error("Error in getQuantity method, businessId: {}, userId: {}, error: {}", businessId, userId, throwable.getMessage());
        return -1;
    }
}


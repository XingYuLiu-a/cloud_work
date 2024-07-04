package org.example.service.impl;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import org.example.apis.CartApi;
import org.example.mapper.FoodMapper;
import org.example.pojo.Cart;
import org.example.pojo.Food;
import org.example.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {
    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);
    @Autowired
    private FoodMapper foodMapper;

    @Resource
    private CartApi cartApi;

    @Override
    @CircuitBreaker(name = "food-service", fallbackMethod = "defaultGetFoodById")
    public Food getFoodById(Integer foodId) {
        return foodMapper.getFoodById(foodId);
    }

    @Override
    @CircuitBreaker(name = "food-service", fallbackMethod = "defaultListFoodByBusinessId")
    public List<Food> listFoodByBusinessId(Integer businessId) {
        List<Food> foodList = foodMapper.listFoodByBusiness(businessId);
        for (Food food : foodList){
            Cart newCart = new Cart();
            newCart.setQuantity(0);
            food.setCart(newCart);
        }
        return foodList;
    }

    @Override
    @CircuitBreaker(name = "food-service", fallbackMethod = "defaultListFood")
    public List<Food> listFood(Integer businessId,Integer userId) {
            List<Food> foodList =foodMapper.listFoodByBusiness(businessId);
            for(Food food :foodList){
                Cart newCart = new Cart();
                Cart cart = cartApi.getCartByUserAndFood(food.getFoodId(),userId);
                if (cart == null){
                    return null;
                }
                newCart.setQuantity(cart.getQuantity());
                newCart.setCartId(cart.getCartId());
                newCart.setFoodId(food.getFoodId());
                newCart.setBusinessId(businessId);
                newCart.setUserId(userId);
                food.setCart(newCart);
            }
            return foodList;
    }


    @Override
    @CircuitBreaker(name = "food-service", fallbackMethod = "defaultAddFood")
    public void addFood(Food food) {
        foodMapper.addFood(food);
    }

    @Override
    @CircuitBreaker(name = "food-service", fallbackMethod = "defaultDeleteFood")
    public void deleteFood(Integer foodId) {
        foodMapper.deleteFood(foodId);
    }


    // Fallback methods

    public Food defaultGetFoodById(Integer foodId, Throwable throwable) {
        logger.error("Error in getFoodById method, foodId: {}, error: {}", foodId, throwable.getMessage());

        return null;
    }

    public List<Food> defaultListFoodByBusinessId(Integer businessId, Throwable throwable) {
        logger.error("Error in listFoodByBusinessId method, businessId: {}, error: {}", businessId, throwable.getMessage());

        return null;
    }

    public List<Food> defaultListFood(Integer businessId, Integer userId, Throwable throwable) {
        logger.error("Error in listFood method, businessId: {}, userId: {}, error: {}", businessId, userId, throwable.getMessage());

        return null;
    }

    public void defaultAddFood(Food food, Throwable throwable) {
        logger.error("Error in addFood method, food: {}, error: {}", food, throwable.getMessage());
    }

    public void defaultDeleteFood(Integer foodId, Throwable throwable) {
        logger.error("Error in deleteFood method, foodId: {}, error: {}", foodId, throwable.getMessage());
    }
}

package org.example.service;


import org.example.pojo.Food;

import java.util.List;

public interface FoodService {
    Food getFoodById(Integer foodId);
    List<Food> listFoodByBusinessId(Integer businessId);
    List<Food> listFood(Integer businessId,Integer userId);


    void addFood(Food food);

    void deleteFood(Integer foodId);

}

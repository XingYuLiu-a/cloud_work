package org.example.controller;


import org.example.pojo.Food;
import org.example.resp.ResultData;
import org.example.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @GetMapping("/getById/{foodId}")
    public ResultData getFoodById(@PathVariable("foodId") Integer foodId){
        Food food = foodService.getFoodById(foodId);

        return ResultData.success(food);
    }

    @GetMapping("/businessId/{businessId}")
    public ResultData listFoodByBusinessId(@PathVariable("businessId") Integer businessId){
        List<Food> foodList = foodService.listFoodByBusinessId(businessId);

        return ResultData.success(foodList);
    }

    @GetMapping("/{businessId}/{userId}")
    public ResultData listFood(@PathVariable("businessId") Integer businessId,@PathVariable("userId") Integer userId){
        List<Food> foodList = foodService.listFood(businessId,userId);

        return ResultData.success(foodList);
    }

    @PostMapping("/")
    public ResultData addFood(@RequestBody Food food){
        foodService.addFood(food);
        return ResultData.success(null);
    }
    @DeleteMapping("/{foodId}")
    public ResultData deleteFood(@PathVariable("foodId") Integer foodId){
       foodService.deleteFood(foodId);
        return ResultData.success(null);
    }

    @GetMapping("/{foodId}")
    public Food getById(@PathVariable("foodId") Integer foodId){
        Food food = foodService.getFoodById(foodId);
        return food;
    }

}

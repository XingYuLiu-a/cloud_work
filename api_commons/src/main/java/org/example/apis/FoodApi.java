package org.example.apis;

import org.example.pojo.Food;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "food-service")
public interface FoodApi {
    @GetMapping("/food/{foodId}")
    Food getById(@PathVariable("foodId") Integer foodId);
}

package org.example.apis;

import org.example.pojo.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;


@FeignClient(value = "cart-service")
public interface CartApi {
    @GetMapping("/cart/getCartByBusiness/{businessId}/{userId}")
    List<Cart> getCartByBusiness(@PathVariable("businessId") Integer businessId, @PathVariable("userId") Integer userId);

    @GetMapping("/cart/getCartByUserAndFood/{foodId}/{userId}")
    Cart getCartByUserAndFood(@PathVariable("foodId") Integer foodId,@PathVariable("userId") Integer userId);

    @PutMapping("/cart/updateQuantity/{cartId}")
    void updateQuantity(@PathVariable("cartId") Integer cartId);
}

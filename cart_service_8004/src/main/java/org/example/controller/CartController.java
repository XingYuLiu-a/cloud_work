package org.example.controller;


import org.example.pojo.Cart;
import org.example.resp.ResultData;
import org.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;


    @GetMapping("/getCart/{foodId}/{userId}")
    public ResultData getCart(@PathVariable("foodId") Integer foodId,@PathVariable("userId") Integer userId){
        Cart cart = cartService.getCart(foodId,userId);

        return ResultData.success(cart);
    }
    @PostMapping("")
    public ResultData addCart(@RequestBody Cart cart){
        cartService.addCart(cart);
        return ResultData.success(null);
    }
    @PutMapping("/updateQuantity/{cartId}")
    public ResultData updateQuantity(@PathVariable("cartId") Integer cartId){
        cartService.updateCartQuantity(cartId);
        return ResultData.success(null);
    }

    @PutMapping("/addQuantity/{cartId}")
    public ResultData addQuantity(@PathVariable("cartId") Integer cartId){
        cartService.addQuantity(cartId);
        return ResultData.success(null);
    }

    @PutMapping("/substrateQuantity/{cartId}")
    public ResultData substrateQuantity(@PathVariable("cartId") Integer cartId){
        cartService.substrateQuantity(cartId);
        return ResultData.success(null);
    }

    @GetMapping("/getCartByBusiness/{businessId}/{userId}")
    public List<Cart> getCartByBusiness(@PathVariable("businessId") Integer businessId,@PathVariable("userId") Integer userId){
        List<Cart> cartList = cartService.getCartByBusiness(businessId,userId);

        return cartList;
    }
    @GetMapping("/getCartByUserAndFood/{foodId}/{userId}")
    public Cart getCartByUserAndFood(@PathVariable("foodId") Integer foodId,@PathVariable("userId") Integer userId){
        Cart cart = cartService.getCart(foodId,userId);

        return cart;
    }
}

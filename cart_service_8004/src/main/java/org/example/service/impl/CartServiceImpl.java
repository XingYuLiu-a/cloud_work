package org.example.service.impl;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.mapper.CartMapper;
import org.example.pojo.Cart;
import org.example.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
    @Autowired
    private CartMapper cartMapper;
    @Override
    @CircuitBreaker(name = "cart-service", fallbackMethod = "defaultGetCart")
    public Cart getCart(Integer foodId,Integer userId) {
        return cartMapper.getCard(foodId,userId);
    }
    @Override
    @CircuitBreaker(name = "cart-service", fallbackMethod = "defaultAddCart")
    public void addCart(Cart cart) {
        cart.setQuantity(0);
        cartMapper.addCart(cart);
    }
    @Override
    @CircuitBreaker(name = "cart-service", fallbackMethod = "defaultUpdateCartQuantity")
    public void updateCartQuantity(Integer cartId) {
        cartMapper.updateCartQuantity(cartId);
    }
    @Override
    @CircuitBreaker(name = "cart-service", fallbackMethod = "defaultAddQuantity")
    public void addQuantity(Integer cartId) {
        cartMapper.addQuantity(cartId);
    }

    @Override
    @CircuitBreaker(name = "cart-service", fallbackMethod = "defaultSubtractQuantity")
    public void substrateQuantity(Integer cartId) {
        Integer quantity = cartMapper.getCartQuantity(cartId);
        if (quantity > 0) {
            cartMapper.substrateQuantity(cartId);
        }else {
            cartMapper.setQuantity(cartId);
        }
    }

    @Override
    @CircuitBreaker(name = "cart-service", fallbackMethod = "defaultGetCartByBusiness")
    public List<Cart> getCartByBusiness(Integer businessId, Integer userId) {
        return cartMapper.getCartByBusiness(businessId,userId);
    }

    // Fallback methods

    public Cart defaultGetCart(Integer foodId, Integer userId, Throwable throwable) {
        logger.error("Error in getCart method, foodId: {}, userId: {}, error: {}", foodId, userId, throwable.getMessage());

        return null;
    }
    public void defaultAddCart(Cart cart, Throwable throwable) {
        logger.error("Error in addCart method, cart: {}, error: {}", cart, throwable.getMessage());
    }
    public void defaultUpdateCartQuantity(Integer cartId, Throwable throwable) {
        logger.error("Error in updateCartQuantity method, cartId: {}, error: {}", cartId, throwable.getMessage());
    }
    public void defaultAddQuantity(Integer cartId, Throwable throwable) {
        logger.error("Error in addQuantity method, cartId: {}, error: {}", cartId, throwable.getMessage());
    }
    public void defaultSubtractQuantity(Integer cartId, Throwable throwable) {
        logger.error("Error in subtractQuantity method, cartId: {}, error: {}", cartId, throwable.getMessage());
    }

    public List<Cart> defaultGetCartByBusiness(Integer businessId, Integer userId, Throwable throwable) {
        logger.error("Error in getCartByBusiness method, businessId: {}, userId: {}, error: {}", businessId, userId, throwable.getMessage());

        return null;
    }
}

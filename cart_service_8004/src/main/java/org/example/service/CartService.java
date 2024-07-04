package org.example.service;


import org.example.pojo.Cart;

import java.util.List;

public interface CartService {
    Cart getCart(Integer foodId,Integer userId);
    void addCart(Cart cart);

    void updateCartQuantity(Integer cartId);
    void addQuantity(Integer cartId);

    void substrateQuantity(Integer cartId);

    List<Cart> getCartByBusiness(Integer businessId,Integer userId);
}

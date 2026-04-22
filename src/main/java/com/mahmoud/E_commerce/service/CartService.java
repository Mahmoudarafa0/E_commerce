package com.mahmoud.E_commerce.service;

import com.mahmoud.E_commerce.dto.AddToCartRequest;
import com.mahmoud.E_commerce.entity.Cart;


public interface CartService {
    void createCartForUser(Long userId);
    Cart addToCart(Long userId, AddToCartRequest addToCartRequest);
    Cart getCart(Long userId);
    void clearCart(Long userId);
    void removeFromCart(Long userId, Long productId);
}

package com.mahmoud.E_commerce.service.impl;

import com.mahmoud.E_commerce.dto.AddToCartRequest;
import com.mahmoud.E_commerce.entity.Cart;
import com.mahmoud.E_commerce.entity.CartItem;
import com.mahmoud.E_commerce.entity.Product;
import com.mahmoud.E_commerce.entity.User;
import com.mahmoud.E_commerce.exception.InsufficientStockException;
import com.mahmoud.E_commerce.repository.CartRepository;
import com.mahmoud.E_commerce.service.CartService;
import com.mahmoud.E_commerce.service.ProductService;
import com.mahmoud.E_commerce.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public void createCartForUser(Long userId) {
        User user = userService.getUserById(userId);
        Cart cart = Cart.builder()
                .user(user)
                .items(new ArrayList<>())
                .build();
        cartRepository.save(cart);
    }

    @Override
    public Cart addToCart(Long userId, AddToCartRequest addToCartRequest) {
        User user = userService.getUserById(userId);
        Product product = productService.getProductById(addToCartRequest.getProductId());

        if (product.getQuantity() < addToCartRequest.getQuantity()) {
            throw new InsufficientStockException("Not enough in the stock");
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElse(Cart.builder().user(user).items(new ArrayList<>()).build());

        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + addToCartRequest.getQuantity());
        } else  {
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(addToCartRequest.getQuantity())
                    .build();
            cart.getItems().add(cartItem);
        }
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCart(Long userId) {
        return cartRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public void removeFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }
}

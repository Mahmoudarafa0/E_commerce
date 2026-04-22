package com.mahmoud.E_commerce.controller;

import com.mahmoud.E_commerce.dto.AddToCartRequest;
import com.mahmoud.E_commerce.dto.CartDTO;
import com.mahmoud.E_commerce.mapper.CartMapper;
import com.mahmoud.E_commerce.service.CartService;
import com.mahmoud.E_commerce.utils.GlobalResponse;
import com.mahmoud.E_commerce.utils.Helper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;
    private final Helper helper;

    @Operation(summary = "User can retrieve his cart")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<GlobalResponse<CartDTO>> getCart() {
        Long userId = helper.extractUserId();
        CartDTO cartDTO = cartMapper.toDTO(cartService.getCart(userId));
        GlobalResponse<CartDTO> response = new GlobalResponse<>(cartDTO);
        response.setMessage("Cart retrieved");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "User can add products to cart")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(path = "/add")
    public ResponseEntity<GlobalResponse<CartDTO>> addToCart(@RequestBody @Valid AddToCartRequest addToCartRequest) {
        Long userId = helper.extractUserId();
        CartDTO cartDTO = cartMapper.toDTO(cartService.addToCart(userId,addToCartRequest));
        GlobalResponse<CartDTO> response = new GlobalResponse<>(cartDTO);
        response.setMessage("Product added to cart successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "User can clear the cart")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping
    public ResponseEntity<GlobalResponse<?>> clearCart() {
        Long userId = helper.extractUserId();
        cartService.clearCart(userId);
        GlobalResponse<?> response = new GlobalResponse<>(null);
        response.setMessage("Cart cleared successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "User can remove product from cart")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(path = "/{productId}")
    public ResponseEntity<GlobalResponse<?>> removeFromCart(@PathVariable Long productId) {
        Long userId = helper.extractUserId();
        cartService.removeFromCart(userId, productId);
        GlobalResponse<?> response = new GlobalResponse<>(null);
        response.setMessage("Product removed successfully");
        return ResponseEntity.ok(response);
    }

}

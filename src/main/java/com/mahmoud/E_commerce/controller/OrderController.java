package com.mahmoud.E_commerce.controller;

import com.mahmoud.E_commerce.dto.CreateOrderRequest;
import com.mahmoud.E_commerce.dto.OrderDTO;
import com.mahmoud.E_commerce.dto.UpdateOrderStatusRequest;
import com.mahmoud.E_commerce.entity.Order;
import com.mahmoud.E_commerce.mapper.OrderMapper;
import com.mahmoud.E_commerce.service.OrderService;
import com.mahmoud.E_commerce.utils.GlobalResponse;
import com.mahmoud.E_commerce.utils.Helper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final Helper helper;

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<GlobalResponse<OrderDTO>> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        Long userId = helper.extractUserId();
        Order order = orderService.createOrder(userId, createOrderRequest);
        GlobalResponse<OrderDTO> response = new GlobalResponse<>(orderMapper.toDTO(order));
        response.setMessage("Order created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Only admin can view all orders")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<GlobalResponse<List<OrderDTO>>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        GlobalResponse<List<OrderDTO>> response = new GlobalResponse<>(orderMapper.toDTOs(orders));
        response.setMessage("All orders retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "User can view his orders")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(path = "/user")
    public ResponseEntity<GlobalResponse<List<OrderDTO>>> getAllUserOrders() {
        Long userId = helper.extractUserId();
        List<Order> orders = orderService.getUserOrders(userId);
        GlobalResponse<List<OrderDTO>> response = new GlobalResponse<>(orderMapper.toDTOs(orders));
        response.setMessage("All orders retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Only admin can update order status")
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping(path = "/{orderId}/status")
    public ResponseEntity<GlobalResponse<OrderDTO>> updateOrderStatus(@PathVariable("orderId") Long orderId, @RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) {
        Order order = orderService.updateOrderStatus(orderId, updateOrderStatusRequest.getOrderStatus());
        GlobalResponse<OrderDTO> response = new GlobalResponse<>(orderMapper.toDTO(order));
        response.setMessage("Order status updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

package com.mahmoud.E_commerce.service;

import com.mahmoud.E_commerce.dto.CreateOrderRequest;
import com.mahmoud.E_commerce.entity.Cart;
import com.mahmoud.E_commerce.entity.Order;
import com.mahmoud.E_commerce.entity.OrderItem;
import com.mahmoud.E_commerce.entity.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(Long userId, CreateOrderRequest createOrderRequest);
    List<OrderItem> createOrderItems(Cart cart, Order order);
    List<Order> getAllOrders();
    List<Order> getUserOrders(Long userId);
    Order updateOrderStatus(Long orderId, OrderStatus orderStatus);
}

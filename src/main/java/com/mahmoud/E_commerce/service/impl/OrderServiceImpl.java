package com.mahmoud.E_commerce.service.impl;

import com.mahmoud.E_commerce.dto.CreateOrderRequest;
import com.mahmoud.E_commerce.entity.*;
import com.mahmoud.E_commerce.entity.enums.OrderStatus;
import com.mahmoud.E_commerce.exception.InsufficientStockException;
import com.mahmoud.E_commerce.repository.OrderRepository;
import com.mahmoud.E_commerce.repository.ProductRepository;
import com.mahmoud.E_commerce.service.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    private final EmailService emailService;
    private final ProductService productService;
    private final ProductRepository productRepository;


    @Override
    public Order createOrder(Long userId, CreateOrderRequest createOrderRequest) {
        User user = userService.getUserById(userId);

        if (!user.isEmailConfirmation()) {
            throw new IllegalStateException("Email not confirmed. Please confirm email before placing order");
        }

        Cart cart = cartService.getCart(userId);
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setAddress(createOrderRequest.getAddress());
        order.setPhoneNumber(createOrderRequest.getPhoneNumber());
        order.setOrderStatus(OrderStatus.PREPARING);
        order.setCreatedAt(LocalDateTime.now());
        List<OrderItem> orderItems = createOrderItems(cart, order);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(userId);

        try {
            emailService.sendOrderConfirmation(savedOrder);
        } catch (Exception e) {
            logger.error("Failed to send order confirmation email for order ID {}", savedOrder.getId(), e);
        }
        return savedOrder;
    }

    @Override
    public List<OrderItem> createOrderItems(Cart cart, Order order) {
        return cart.getItems().stream().map(cartItem -> {
            Product product = productService.getProductById(cartItem.getProduct().getId());

            if (product.getQuantity() == null) {
                throw new IllegalStateException("Quantity not set for product : " + product.getName());
            }

            if (cartItem.getQuantity() > product.getQuantity()) {
                throw new InsufficientStockException("not enough stock for product : " + product.getName());
            }

            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepository.save(product);
            return OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(product.getPrice())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }
}

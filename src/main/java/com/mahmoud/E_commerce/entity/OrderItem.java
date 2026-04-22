package com.mahmoud.E_commerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="order_id", nullable = false)
    private Order order;
    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private Product product;
    private Integer quantity;
    private BigDecimal price;
}
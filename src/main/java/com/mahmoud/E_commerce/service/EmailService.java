package com.mahmoud.E_commerce.service;

import com.mahmoud.E_commerce.entity.Order;
import com.mahmoud.E_commerce.entity.User;

public interface EmailService {
    void sendConfirmationCode(User user);
    String generateConfirmationCode();
    void sendOrderConfirmation(Order order);
}

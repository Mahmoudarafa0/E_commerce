package com.mahmoud.E_commerce.service.impl;

import com.mahmoud.E_commerce.entity.Order;
import com.mahmoud.E_commerce.entity.User;
import com.mahmoud.E_commerce.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("spring.mail.username")
    private String fromEmail;

    @Override
    public void sendConfirmationCode(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("confirm your email");
        message.setText("please confirm your email by entering this code " + user.getConfirmationCode());
        mailSender.send(message);
    }

    @Override
    public String generateConfirmationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    @Override
    public void sendOrderConfirmation(Order order){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(order.getUser().getEmail());
        message.setSubject("Order confirmation");
        message.setText("Your order has been confirmed. Order ID " + order.getId());
        mailSender.send(message);
    }
}

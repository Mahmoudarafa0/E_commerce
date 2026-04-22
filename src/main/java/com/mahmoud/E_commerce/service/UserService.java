package com.mahmoud.E_commerce.service;

import com.mahmoud.E_commerce.dto.ChangePasswordRequest;
import com.mahmoud.E_commerce.entity.User;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(Long id);
    void changePassword(ChangePasswordRequest changePasswordRequest);
    void confirmEmail(String confirmationCode);
}

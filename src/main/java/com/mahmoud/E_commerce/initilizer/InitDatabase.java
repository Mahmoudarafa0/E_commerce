package com.mahmoud.E_commerce.initilizer;

import com.mahmoud.E_commerce.entity.User;
import com.mahmoud.E_commerce.entity.enums.Role;
import com.mahmoud.E_commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class InitDatabase implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String ... args) throws Exception {

        User user = User.builder()
                .username("admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ADMIN)
                .emailConfirmation(true)
                .build();
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return;
        }
        userRepository.save(user);
        log.info("database has been initialized");
    }
}

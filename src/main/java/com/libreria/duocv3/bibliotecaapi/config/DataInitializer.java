package com.libreria.duocv3.bibliotecaapi.config;

import com.libreria.duocv3.bibliotecaapi.user.Role;
import com.libreria.duocv3.bibliotecaapi.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedAdmin(UserService userService, AppProps appProps) {
        return args -> {
            String name = appProps.getAdminName();
            String email = appProps.getAdminEmail();
            String pwd = appProps.getAdminPassword();
            userService.createIfNotExists(name, email, pwd, Role.ROLE_ADMIN);
        };
    }
}

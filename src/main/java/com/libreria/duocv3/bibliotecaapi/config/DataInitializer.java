package com.libreria.duocv3.bibliotecaapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.libreria.duocv3.bibliotecaapi.user.Role;
import com.libreria.duocv3.bibliotecaapi.user.User;
import com.libreria.duocv3.bibliotecaapi.user.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AppProps app;

    public DataInitializer(UserRepository repo, PasswordEncoder encoder, AppProps app) {
        this.repo = repo; this.encoder = encoder; this.app = app;
    }

    @Override
    public void run(String... args) {
        repo.findByEmail(app.getAdminEmail()).ifPresentOrElse(
            u -> {}, // ya existe
            () -> {
                User admin = new User();
                admin.setName(app.getAdminName());
                admin.setEmail(app.getAdminEmail());
                admin.setRole(Role.ROLE_ADMIN);
                admin.setPassword(encoder.encode(app.getAdminPassword()));
                repo.save(admin);
                System.out.println("👤 Admin seed creado: " + admin.getEmail());
            }
        );
    }
}

package com.libreria.duocv3.bibliotecaapi.user;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.libreria.duocv3.bibliotecaapi.user.dto.CreateUserRequest;
import com.libreria.duocv3.bibliotecaapi.user.dto.UserResponse;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    public User loadByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no existe: " + email));
    }

    public UserResponse createClient(CreateUserRequest req) {
        Optional<User> existing = userRepo.findByEmail(req.email());
        if (existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ya registrado");
        }
        User u = new User();
        u.setName(req.name());
        u.setEmail(req.email());
        u.setRole(Role.ROLE_USER);
        u.setPassword(encoder.encode(req.password()));
        u = userRepo.save(u);
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole().name());
    }

    public void changeOwnPassword(String email, String currentPassword, String newPassword) {
        User u = loadByEmail(email);
        if (!encoder.matches(currentPassword, u.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña actual inválida");
        }
        u.setPassword(encoder.encode(newPassword));
        userRepo.save(u);
    }

    public UserResponse findUserByEmailResponse(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }

}

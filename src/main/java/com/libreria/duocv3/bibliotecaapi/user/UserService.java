package com.libreria.duocv3.bibliotecaapi.user;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.libreria.duocv3.bibliotecaapi.security.JwtUtil;
import com.libreria.duocv3.bibliotecaapi.user.dto.CreateUserRequest;
import com.libreria.duocv3.bibliotecaapi.user.dto.UserResponse;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public UserService(UserRepository userRepo, PasswordEncoder encoder, JwtUtil jwt) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    // ==============================
    //  CARGA DE USUARIO POR EMAIL
    // ==============================
    public User loadByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no existe: " + email));
    }

    // ==============================
    // LOGIN (VALIDACIÓN + JWT)
    // ==============================
    public String login(String email, String rawPassword) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        if (!encoder.matches(rawPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        // Generación del Token JWT
        return jwt.generate(user.getEmail(), user.getRole().name());
    }

    // ==============================
    // CREAR USUARIO NORMAL (ROLE_USER)
    // ==============================
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

        return new UserResponse(
                u.getId(), 
                u.getName(), 
                u.getEmail(), 
                u.getRole().name()
        );
    }

    // ==============================
    // CAMBIO DE CONTRASEÑA
    // ==============================
    public void changeOwnPassword(String email, String currentPassword, String newPassword) {
        User u = loadByEmail(email);

        if (!encoder.matches(currentPassword, u.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña actual inválida");
        }

        u.setPassword(encoder.encode(newPassword));
        userRepo.save(u);
    }

    // ==============================
    // OBTENER USUARIO COMO DTO
    // ==============================
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

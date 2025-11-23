package com.libreria.duocv3.bibliotecaapi.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestSecureController {

    @GetMapping("/protected")
    public String protectedEndpoint() {
        return "Acceso concedido: JWT válido ✔️";
    }
}

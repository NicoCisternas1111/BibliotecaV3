package com.libreria.duocv3.bibliotecaapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProps {

    private final Admin admin = new Admin();

    public Admin getAdmin() { return admin; }

    public static class Admin {
        private String name;
        private String email;
        private String password;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public String getAdminName() { return admin.name; }
    public String getAdminEmail() { return admin.email; }
    public String getAdminPassword() { return admin.password; }
}

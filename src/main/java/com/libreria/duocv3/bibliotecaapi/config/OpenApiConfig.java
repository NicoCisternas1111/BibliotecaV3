package com.libreria.duocv3.bibliotecaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.List;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI apiInfo() {

                final String schemeName = "BearerAuth";

                return new OpenAPI()
                                .servers(List.of(
                                                new Server().url(
                                                                "http://biblioteca-backend-env.eba-y69gtg3a.us-east-1.elasticbeanstalk.com")
                                                                .description("Servidor de Producción (AWS)"),
                                                new Server().url("http://localhost:8080")
                                                                .description("Servidor Local")))
                                .info(new Info()
                                                .title("Biblioteca V3 API")
                                                .version("1.0.0")
                                                .description("Catálogo público + administración protegida con JWT.")
                                                .contact(new Contact()
                                                                .name("Soporte Técnico")
                                                                .email("contacto@biblioteca.cl")
                                                                .url("https://biblioteca.cl")))
                                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                                .components(new Components()
                                                .addSecuritySchemes(
                                                                schemeName,
                                                                new SecurityScheme()
                                                                                .name(schemeName)
                                                                                .type(SecurityScheme.Type.HTTP)
                                                                                .scheme("bearer")
                                                                                .bearerFormat("JWT")));
        }
}
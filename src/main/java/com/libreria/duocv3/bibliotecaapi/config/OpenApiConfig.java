package com.libreria.duocv3.bibliotecaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI apiInfo() {
    return new OpenAPI().info(
      new Info()
        .title("Biblioteca V3 API")
        .version("v1")
        .description("Catálogo público + administración protegida con JWT")
    );
  }
}

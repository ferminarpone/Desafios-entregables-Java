package com.coder.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Ecommerce Coder API",
                version = "1.0",
                description = "CRUD of clients, products, cart and invoices."
        )
)
public class OpenApiConfig {
}
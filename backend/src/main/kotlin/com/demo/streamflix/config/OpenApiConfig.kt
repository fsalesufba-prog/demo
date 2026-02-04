package com.demo.streamflix.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "DEMO StreamFlix API",
        version = "1.0.0",
        description = "API para aplicativo de streaming de TV peruana",
        contact = Contact(
            name = "Suporte StreamFlix",
            email = "suporte@streamflix.demo",
            url = "https://streamflix.demo"
        ),
        license = License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = [
        Server(
            url = "http://localhost:8080/api",
            description = "Servidor de Desenvolvimento"
        ),
        Server(
            url = "https://api.streamflix.demo",
            description = "Servidor de Produção"
        )
    ]
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Token JWT obtido no login"
)
class OpenApiConfig
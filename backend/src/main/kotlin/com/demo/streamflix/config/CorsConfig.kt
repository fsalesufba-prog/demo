package com.demo.streamflix.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Value("\${app.cors.allowed-origins:*}")
    private lateinit var allowedOrigins: String

    @Value("\${app.cors.allowed-methods:*}")
    private lateinit var allowedMethods: String

    @Value("\${app.cors.allowed-headers:*}")
    private lateinit var allowedHeaders: String

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        // Configurar origens permitidas
        if (allowedOrigins != "*") {
            config.allowedOrigins = allowedOrigins.split(",").map { it.trim() }
        } else {
            config.allowedOriginPatterns = listOf("*")
        }

        // Configurar métodos HTTP permitidos
        if (allowedMethods != "*") {
            config.allowedMethods = allowedMethods.split(",").map { it.trim() }
        } else {
            config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
        }

        // Configurar headers permitidos
        if (allowedHeaders != "*") {
            config.allowedHeaders = allowedHeaders.split(",").map { it.trim() }
        } else {
            config.allowedHeaders = listOf("*")
        }

        config.allowCredentials = true
        config.maxAge = 3600L // 1 hora

        source.registerCorsConfiguration("/api/**", config)
        return CorsFilter(source)
    }
}
package com.demo.streamflix.config

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.Key

@Configuration
class JwtConfig {

    @Value("\${app.jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${app.jwt.expiration}")
    private val jwtExpiration: Long = 86400000 // 24 horas

    @Bean
    fun jwtSecretKey(): Key {
        return Keys.hmacShaKeyFor(jwtSecret.toByteArray())
    }

    fun getJwtSecret(): String = jwtSecret

    fun getJwtExpiration(): Long = jwtExpiration
}
package com.demo.streamflix.service

import com.demo.streamflix.config.JwtConfig
import com.demo.streamflix.security.UserDetailsImpl
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JwtService(
    private val jwtConfig: JwtConfig
) {

    private val secretKey = jwtConfig.jwtSecretKey()

    fun generateToken(authentication: Authentication): String {
        val userDetails = authentication.principal as UserDetailsImpl
        return generateToken(userDetails)
    }

    fun generateToken(userDetails: UserDetailsImpl): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtConfig.getJwtExpiration())

        return Jwts.builder()
            .setSubject(userDetails.username)
            .claim("id", userDetails.id)
            .claim("email", userDetails.email)
            .claim("fullName", userDetails.fullName)
            .claim("isAdmin", userDetails.isAdmin)
            .claim("authorities", userDetails.authorities.map { it.authority })
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateTokenFromUsername(username: String): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtConfig.getJwtExpiration())

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun getUsernameFromToken(token: String): String {
        return getClaimsFromToken(token).subject
    }

    fun getUserIdFromToken(token: String): Long {
        val claims = getClaimsFromToken(token)
        return claims["id"] as Long
    }

    fun getClaimsFromToken(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getExpirationFromToken(token: String): Date {
        return getClaimsFromToken(token).expiration
    }

    fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationFromToken(token)
        return expiration.before(Date())
    }

    fun getAuthoritiesFromToken(token: String): List<GrantedAuthority> {
        val claims = getClaimsFromToken(token)
        val authorities = claims.get("authorities", List::class.java) as? List<String> ?: emptyList()
        
        return authorities.map { 
            org.springframework.security.core.authority.SimpleGrantedAuthority(it) 
        }
    }

    fun refreshToken(token: String): String {
        if (!validateToken(token)) {
            throw IllegalArgumentException("Token inválido")
        }

        val username = getUsernameFromToken(token)
        return generateTokenFromUsername(username)
    }
}
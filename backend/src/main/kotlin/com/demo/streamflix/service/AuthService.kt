package com.demo.streamflix.service

import com.demo.streamflix.config.JwtConfig
import com.demo.streamflix.model.entity.UserEntity
import com.demo.streamflix.model.response.AuthResponse
import com.demo.streamflix.exception.AuthException
import com.demo.streamflix.repository.UserRepository
import com.demo.streamflix.security.UserDetailsImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class AuthService(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtConfig: JwtConfig
) {

    fun authenticate(email: String, password: String): AuthResponse {
        // Buscar usuário
        val user = userRepository.findByEmail(email)
            ?: throw AuthException("Credenciais inválidas")

        // Verificar se usuário está ativo
        if (!user.isActive) {
            throw AuthException("Usuário desativado. Entre em contato com o administrador.")
        }

        // Autenticar com Spring Security
        val authentication: Authentication = try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(email, password)
            )
        } catch (e: Exception) {
            throw AuthException("Credenciais inválidas")
        }

        // Gerar token JWT
        val userDetails = authentication.principal as UserDetailsImpl
        val token = jwtService.generateToken(userDetails)
        
        // Atualizar último login
        user.lastLogin = LocalDateTime.now()
        userRepository.save(user)

        return AuthResponse(
            token = token,
            tokenType = "Bearer",
            expiresIn = jwtConfig.getJwtExpiration(),
            user = user.toDto()
        )
    }

    fun validateToken(token: String): Boolean {
        return try {
            jwtService.validateToken(token)
        } catch (e: Exception) {
            false
        }
    }

    fun getCurrentUser(): UserEntity? {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication != null && authentication.isAuthenticated) {
            val userDetails = authentication.principal as? UserDetailsImpl
            userDetails?.let { userRepository.findByEmail(it.username) }
        } else {
            null
        }
    }

    fun getCurrentUserId(): Long? {
        return getCurrentUser()?.id
    }

    fun logout() {
        SecurityContextHolder.clearContext()
        // Em produção, você pode querer adicionar o token a uma blacklist
    }
}
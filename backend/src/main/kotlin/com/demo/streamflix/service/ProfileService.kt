package com.demo.streamflix.service

import com.demo.streamflix.model.dto.UserDto
import com.demo.streamflix.model.request.UpdateProfileRequest
import com.demo.streamflix.exception.NotFoundException
import com.demo.streamflix.exception.ValidationException
import com.demo.streamflix.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class ProfileService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val subscriptionService: SubscriptionService,
    private val validationService: ValidationService
) {

    fun getProfile(email: String): UserDto {
        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException("Usuário não encontrado com email: $email")
        return user.toDto()
    }

    fun updateProfile(email: String, request: UpdateProfileRequest): UserDto {
        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException("Usuário não encontrado com email: $email")

        // Atualizar campos básicos
        user.fullName = request.fullName ?: user.fullName
        user.phone = request.phone ?: user.phone
        
        // Se email foi fornecido e é diferente, verificar se já existe
        request.email?.let { newEmail ->
            if (newEmail != user.email) {
                if (userRepository.existsByEmail(newEmail)) {
                    throw ValidationException("Email já está em uso: $newEmail")
                }
                user.email = newEmail
            }
        }

        user.updatedAt = LocalDateTime.now()
        val updatedUser = userRepository.save(user)
        
        return updatedUser.toDto()
    }

    fun changePassword(email: String, currentPassword: String, newPassword: String) {
        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException("Usuário não encontrado com email: $email")

        // Verificar senha atual
        if (!passwordEncoder.matches(currentPassword, user.password)) {
            throw ValidationException("Senha atual incorreta")
        }

        // Validar nova senha
        if (newPassword.length < 6) {
            throw ValidationException("A nova senha deve ter pelo menos 6 caracteres")
        }

        // Atualizar senha
        user.password = passwordEncoder.encode(newPassword)
        user.updatedAt = LocalDateTime.now()
        
        userRepository.save(user)
    }

    fun getSubscriptionStatus(email: String): Map<String, Any> {
        return validationService.getMembershipStatus(email)
    }

    fun updateLastLogin(email: String) {
        val user = userRepository.findByEmail(email)
        user?.let {
            it.lastLogin = LocalDateTime.now()
            userRepository.save(it)
        }
    }

    fun getProfileWithSubscription(email: String): Map<String, Any> {
        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException("Usuário não encontrado com email: $email")
        
        val subscription = subscriptionService.getActiveSubscription(email)
        val membershipStatus = validationService.getMembershipStatus(email)
        
        return mapOf(
            "user" to user.toDto(),
            "subscription" to subscription,
            "membershipStatus" to membershipStatus,
            "canAccessContent" to membershipStatus["valid"] == true
        )
    }
}
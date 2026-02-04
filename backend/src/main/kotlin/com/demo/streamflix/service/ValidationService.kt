package com.demo.streamflix.service

import com.demo.streamflix.model.entity.UserEntity
import com.demo.streamflix.repository.SubscriptionRepository
import com.demo.streamflix.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ValidationService(
    private val userRepository: UserRepository,
    private val subscriptionRepository: SubscriptionRepository
) {

    fun validateMembership(): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            return false
        }

        val principal = authentication.principal
        val username = when (principal) {
            is UserDetails -> principal.username
            is String -> principal
            else -> return false
        }

        val user = userRepository.findByEmail(username) ?: return false
        
        // Verificar se usuário está ativo
        if (!user.isActive) {
            return false
        }

        // Se for admin, não precisa de validação de assinatura
        if (user.isAdmin) {
            return true
        }

        // Verificar assinatura ativa
        val activeSubscription = subscriptionRepository.findActiveSubscription(user)
        return activeSubscription != null && activeSubscription.endDate.isAfter(LocalDate.now())
    }

    fun validateUserActive(email: String): Boolean {
        val user = userRepository.findByEmail(email) ?: return false
        return user.isActive
    }

    fun validateUserActive(userId: Long): Boolean {
        val user = userRepository.findById(userId).orElse(null) ?: return false
        return user.isActive
    }

    fun validateSubscription(user: UserEntity): Boolean {
        // Verificar se usuário está ativo
        if (!user.isActive) {
            return false
        }

        // Admin sempre tem acesso
        if (user.isAdmin) {
            return true
        }

        // Verificar assinatura ativa
        val activeSubscription = subscriptionRepository.findActiveSubscription(user)
        return activeSubscription != null && activeSubscription.endDate.isAfter(LocalDate.now())
    }

    fun getMembershipStatus(email: String): Map<String, Any> {
        val user = userRepository.findByEmail(email) ?: return mapOf(
            "valid" to false,
            "reason" to "Usuário não encontrado"
        )

        if (!user.isActive) {
            return mapOf(
                "valid" to false,
                "reason" to "Usuário desativado"
            )
        }

        if (user.isAdmin) {
            return mapOf(
                "valid" to true,
                "reason" to "Usuário administrador",
                "isAdmin" to true
            )
        }

        val activeSubscription = subscriptionRepository.findActiveSubscription(user)
        val hasActiveSubscription = activeSubscription != null && activeSubscription.endDate.isAfter(LocalDate.now())

        return if (hasActiveSubscription) {
            mapOf(
                "valid" to true,
                "reason" to "Assinatura ativa",
                "subscription" to mapOf(
                    "startDate" to activeSubscription!!.startDate.toString(),
                    "endDate" to activeSubscription.endDate.toString(),
                    "daysRemaining" to LocalDate.now().until(activeSubscription.endDate).days
                )
            )
        } else {
            mapOf(
                "valid" to false,
                "reason" to "Assinatura expirada ou não encontrada"
            )
        }
    }

    fun getCurrentUserMembershipStatus(): Map<String, Any> {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            return mapOf("valid" to false, "reason" to "Não autenticado")
        }

        val username = when (val principal = authentication.principal) {
            is UserDetails -> principal.username
            is String -> principal
            else -> return mapOf("valid" to false, "reason" to "Tipo de autenticação inválido")
        }

        return getMembershipStatus(username)
    }

    fun canAccessChannel(userId: Long, channelId: Long? = null): Boolean {
        val user = userRepository.findById(userId).orElse(null) ?: return false
        return validateSubscription(user)
        // Aqui você pode adicionar lógica adicional baseada no canal específico
        // Por exemplo, alguns canais podem ser premium e requerer assinatura especial
    }

    fun validateAndGetUser(email: String): UserEntity? {
        val user = userRepository.findByEmail(email) ?: return null
        return if (validateSubscription(user)) user else null
    }
}
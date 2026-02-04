package com.demo.streamflix.service

import com.demo.streamflix.model.dto.SubscriptionDto
import com.demo.streamflix.model.entity.SubscriptionEntity
import com.demo.streamflix.model.entity.UserEntity
import com.demo.streamflix.model.request.SubscriptionRequest
import com.demo.streamflix.exception.NotFoundException
import com.demo.streamflix.exception.ValidationException
import com.demo.streamflix.repository.SubscriptionRepository
import com.demo.streamflix.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Service
@Transactional
class SubscriptionService(
    private val subscriptionRepository: SubscriptionRepository,
    private val userRepository: UserRepository
) {

    fun createSubscription(request: SubscriptionRequest): SubscriptionDto {
        val user = userRepository.findById(request.userId)
            .orElseThrow { NotFoundException("Usuário não encontrado com ID: ${request.userId}") }

        // Verificar se usuário já tem assinatura ativa
        val activeSubscription = getActiveSubscription(user.id!!)
        if (activeSubscription != null) {
            throw ValidationException("Usuário já possui uma assinatura ativa")
        }

        val startDate = request.startDate ?: LocalDate.now()
        val endDate = startDate.plusDays(request.durationDays.toLong())

        val subscription = SubscriptionEntity(
            user = user,
            startDate = startDate,
            endDate = endDate,
            status = SubscriptionEntity.SubscriptionStatus.ACTIVE
        )

        val savedSubscription = subscriptionRepository.save(subscription)
        return savedSubscription.toDto()
    }

    fun getUserSubscriptions(userId: Long): List<SubscriptionDto> {
        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("Usuário não encontrado com ID: $userId") }

        return subscriptionRepository.findByUserOrderByStartDateDesc(user)
            .map { it.toDto() }
    }

    fun getUserSubscriptionsByEmail(email: String): List<SubscriptionDto> {
        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException("Usuário não encontrado com email: $email")

        return subscriptionRepository.findByUserOrderByStartDateDesc(user)
            .map { it.toDto() }
    }

    fun getActiveSubscription(userId: Long): SubscriptionDto? {
        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("Usuário não encontrado com ID: $userId") }

        val subscription = subscriptionRepository.findActiveSubscription(user)
        return subscription?.toDto()
    }

    fun getActiveSubscription(email: String): SubscriptionDto? {
        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException("Usuário não encontrado com email: $email")

        val subscription = subscriptionRepository.findActiveSubscription(user)
        return subscription?.toDto()
    }

    fun extendSubscription(subscriptionId: Long, days: Int): SubscriptionDto {
        val subscription = subscriptionRepository.findById(subscriptionId)
            .orElseThrow { NotFoundException("Assinatura não encontrada com ID: $subscriptionId") }

        if (subscription.status != SubscriptionEntity.SubscriptionStatus.ACTIVE) {
            throw ValidationException("Somente assinaturas ativas podem ser estendidas")
        }

        subscription.endDate = subscription.endDate.plusDays(days.toLong())
        subscription.updatedAt = LocalDateTime.now()

        val updatedSubscription = subscriptionRepository.save(subscription)
        return updatedSubscription.toDto()
    }

    fun cancelSubscription(subscriptionId: Long): SubscriptionDto {
        val subscription = subscriptionRepository.findById(subscriptionId)
            .orElseThrow { NotFoundException("Assinatura não encontrada com ID: $subscriptionId") }

        if (subscription.status == SubscriptionEntity.SubscriptionStatus.CANCELLED) {
            throw ValidationException("Assinatura já está cancelada")
        }

        subscription.status = SubscriptionEntity.SubscriptionStatus.CANCELLED
        subscription.updatedAt = LocalDateTime.now()

        val updatedSubscription = subscriptionRepository.save(subscription)
        return updatedSubscription.toDto()
    }

    fun getExpiringSubscriptions(days: Int): List<SubscriptionDto> {
        val expirationDate = LocalDate.now().plusDays(days.toLong())
        return subscriptionRepository.findExpiringSubscriptions(expirationDate)
            .map { it.toDto() }
    }

    fun validateUserSubscription(userId: Long): Boolean {
        val subscription = subscriptionRepository.findActiveSubscriptionByUserId(userId)
        return subscription != null && subscription.endDate.isAfter(LocalDate.now())
    }

    fun validateUserSubscription(email: String): Boolean {
        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException("Usuário não encontrado com email: $email")
        
        return validateUserSubscription(user.id!!)
    }

    fun updateSubscriptionStatus() {
        val expiredSubscriptions = subscriptionRepository.findByEndDateBeforeAndStatus(
            LocalDate.now(),
            SubscriptionEntity.SubscriptionStatus.ACTIVE
        )

        expiredSubscriptions.forEach { subscription ->
            subscription.status = SubscriptionEntity.SubscriptionStatus.EXPIRED
            subscription.updatedAt = LocalDateTime.now()
        }

        subscriptionRepository.saveAll(expiredSubscriptions)
    }

    fun getSubscriptionStats(): Map<String, Any> {
        val total = subscriptionRepository.count()
        val active = subscriptionRepository.countByStatus(SubscriptionEntity.SubscriptionStatus.ACTIVE)
        val expired = subscriptionRepository.countByStatus(SubscriptionEntity.SubscriptionStatus.EXPIRED)
        val cancelled = subscriptionRepository.countByStatus(SubscriptionEntity.SubscriptionStatus.CANCELLED)

        return mapOf(
            "total" to total,
            "active" to active,
            "expired" to expired,
            "cancelled" to cancelled,
            "activePercentage" to if (total > 0) (active * 100.0 / total) else 0.0
        )
    }

    fun searchSubscriptions(
        query: String?,
        status: SubscriptionEntity.SubscriptionStatus?,
        page: Int,
        size: Int
    ): Page<SubscriptionDto> {
        val pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "startDate"))
        return subscriptionRepository.searchSubscriptions(query, status, pageable)
            .map { it.toDto() }
    }
}
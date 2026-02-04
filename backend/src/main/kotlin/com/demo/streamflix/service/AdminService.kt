package com.demo.streamflix.service

import com.demo.streamflix.model.response.DashboardStats
import com.demo.streamflix.exception.NotFoundException
import com.demo.streamflix.repository.*
import com.demo.streamflix.security.UserDetailsImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class AdminService(
    private val userRepository: UserRepository,
    private val channelRepository: ChannelRepository,
    private val categoryRepository: CategoryRepository,
    private val subscriptionRepository: SubscriptionRepository,
    private val accessLogRepository: AccessLogRepository,
    private val jwtService: JwtService
) {

    fun getDashboardStats(): DashboardStats {
        val totalUsers = userRepository.count()
        val activeUsers = userRepository.countByIsActive(true)
        val totalChannels = channelRepository.countByIsActiveTrue()
        val totalCategories = categoryRepository.countByIsActiveTrue()
        
        val subscriptionStats = subscriptionRepository.countByStatus(
            com.demo.streamflix.model.entity.SubscriptionEntity.SubscriptionStatus.ACTIVE
        )
        
        val newUsersLast30Days = userRepository.countNewUsersLast30Days()
        val newChannelsLast30Days = channelRepository.countNewChannelsLast30Days()
        val newSubscriptionsLast30Days = subscriptionRepository.countNewSubscriptionsLast30Days()
        
        val todayLogins = accessLogRepository.countTodayLogins()
        
        return DashboardStats(
            totalUsers = totalUsers,
            activeUsers = activeUsers,
            totalChannels = totalChannels,
            totalCategories = totalCategories,
            activeSubscriptions = subscriptionStats,
            newUsersLast30Days = newUsersLast30Days,
            newChannelsLast30Days = newChannelsLast30Days,
            newSubscriptionsLast30Days = newSubscriptionsLast30Days,
            todayLogins = todayLogins,
            lastUpdated = LocalDateTime.now()
        )
    }

    fun generateImpersonationToken(userId: Long): String {
        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("Usuário não encontrado com ID: $userId") }
        
        val userDetails = UserDetailsImpl.build(user)
        return jwtService.generateToken(userDetails)
    }

    fun toggleChannelFeatured(channelId: Long) {
        // Implementar lógica para destacar canais
        // Você pode adicionar um campo 'featured' na entidade ChannelEntity
        // Ou usar uma tabela separada para canais em destaque
        // Por enquanto, vamos apenas marcar/desmarcar um campo
        val channel = channelRepository.findById(channelId)
            .orElseThrow { NotFoundException("Canal não encontrado com ID: $channelId") }
        
        // Aqui você pode adicionar lógica para alternar o status de destaque
        // Exemplo: channel.isFeatured = !channel.isFeatured
        // channelRepository.save(channel)
    }

    fun makeUserAdmin(userId: Long) {
        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("Usuário não encontrado com ID: $userId") }
        
        user.isAdmin = true
        user.updatedAt = LocalDateTime.now()
        
        userRepository.save(user)
    }

    fun getAccessLogs(page: Int, size: Int): Map<String, Any> {
        val pageable = PageRequest.of(page, size)
        val logs = accessLogRepository.findAllByOrderByCreatedAtDesc(pageable)
        val total = accessLogRepository.count()
        
        return mapOf(
            "logs" to logs.content,
            "total" to total,
            "page" to page,
            "size" to size,
            "totalPages" to logs.totalPages
        )
    }

    fun getSystemHealth(): Map<String, Any> {
        val databaseConnected = true // Você pode adicionar verificação real do banco
        val memoryUsage = Runtime.getRuntime()
        val uptime = System.currentTimeMillis() - startTime
        
        return mapOf(
            "database" to mapOf("connected" to databaseConnected),
            "memory" to mapOf(
                "total" to memoryUsage.totalMemory(),
                "free" to memoryUsage.freeMemory(),
                "used" to memoryUsage.totalMemory() - memoryUsage.freeMemory(),
                "max" to memoryUsage.maxMemory()
            ),
            "uptime" to uptime,
            "timestamp" to LocalDateTime.now()
        )
    }

    companion object {
        val startTime = System.currentTimeMillis()
    }
}
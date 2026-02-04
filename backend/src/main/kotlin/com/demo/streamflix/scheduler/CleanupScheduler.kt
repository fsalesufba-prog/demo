package com.demo.streamflix.scheduler

import com.demo.streamflix.repository.AccessLogRepository
import com.demo.streamflix.repository.SearchLogRepository
import com.demo.streamflix.service.SubscriptionService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class CleanupScheduler(
    private val accessLogRepository: AccessLogRepository,
    private val searchLogRepository: SearchLogRepository,
    private val subscriptionService: SubscriptionService
) {

    private val log = LoggerFactory.getLogger(CleanupScheduler::class.java)

    /**
     * Executa todos os dias à meia-noite para atualizar status de assinaturas
     */
    @Scheduled(cron = "0 0 0 * * *") // Meia-noite todos os dias
    @Transactional
    fun updateSubscriptionStatus() {
        log.info("Iniciando atualização de status de assinaturas...")
        subscriptionService.updateSubscriptionStatus()
        log.info("Atualização de status de assinaturas concluída")
    }

    /**
     * Executa todo dia 1 do mês para limpar logs antigos
     */
    @Scheduled(cron = "0 0 0 1 * *") // Meia-noite do dia 1 de cada mês
    @Transactional
    fun cleanupOldLogs() {
        log.info("Iniciando limpeza de logs antigos...")
        
        val cutoffDate = LocalDateTime.now().minusMonths(3) // Mantém 3 meses de logs
        
        val accessLogsDeleted = accessLogRepository.deleteByCreatedAtBefore(cutoffDate)
        val searchLogsDeleted = searchLogRepository.deleteByCreatedAtBefore(cutoffDate)
        
        log.info("Limpeza concluída: $accessLogsDeleted logs de acesso e $searchLogsDeleted logs de busca removidos")
    }

    /**
     * Executa a cada hora para verificar assinaturas prestes a expirar
     */
    @Scheduled(cron = "0 0 * * * *") // A cada hora
    fun checkExpiringSubscriptions() {
        log.debug("Verificando assinaturas prestes a expirar...")
        val expiringSubscriptions = subscriptionService.getExpiringSubscriptions(3) // 3 dias
        if (expiringSubscriptions.isNotEmpty()) {
            log.warn("Encontradas ${expiringSubscriptions.size} assinaturas prestes a expirar")
            // Aqui você poderia enviar notificações por email
        }
    }
}
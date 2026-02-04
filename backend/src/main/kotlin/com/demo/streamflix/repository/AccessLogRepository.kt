package com.demo.streamflix.repository

import com.demo.streamflix.model.entity.AccessLogEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface AccessLogRepository : JpaRepository<AccessLogEntity, Long> {

    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<AccessLogEntity>

    fun findByUserIdOrderByCreatedAtDesc(userId: Long, pageable: Pageable): Page<AccessLogEntity>

    @Query("SELECT COUNT(a) FROM AccessLogEntity a WHERE DATE(a.createdAt) = CURRENT_DATE AND a.action = 'LOGIN'")
    fun countTodayLogins(): Long

    @Query("SELECT a FROM AccessLogEntity a WHERE a.user.id = :userId AND a.channel.id = :channelId ORDER BY a.createdAt DESC")
    fun findUserChannelAccess(
        @Param("userId") userId: Long,
        @Param("channelId") channelId: Long,
        pageable: Pageable
    ): Page<AccessLogEntity>

    @Query("SELECT COUNT(DISTINCT a.user.id) FROM AccessLogEntity a WHERE DATE(a.createdAt) = :date")
    fun countUniqueUsersByDate(@Param("date") date: LocalDate): Long

    @Query("SELECT a.action, COUNT(a) FROM AccessLogEntity a WHERE DATE(a.createdAt) = CURRENT_DATE GROUP BY a.action")
    fun countActionsToday(): List<Array<Any>>

    fun deleteByCreatedAtBefore(date: java.time.LocalDateTime): Long
}
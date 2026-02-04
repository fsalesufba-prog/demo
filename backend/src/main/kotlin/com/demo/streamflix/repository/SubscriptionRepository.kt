package com.demo.streamflix.repository

import com.demo.streamflix.model.entity.SubscriptionEntity
import com.demo.streamflix.model.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface SubscriptionRepository : JpaRepository<SubscriptionEntity, Long> {

    fun findByUserOrderByStartDateDesc(user: UserEntity): List<SubscriptionEntity>

    @Query("SELECT s FROM SubscriptionEntity s WHERE s.user = :user " +
           "AND s.status = 'ACTIVE' " +
           "AND s.endDate >= CURRENT_DATE")
    fun findActiveSubscription(@Param("user") user: UserEntity): SubscriptionEntity?

    @Query("SELECT s FROM SubscriptionEntity s WHERE s.user.id = :userId " +
           "AND s.status = 'ACTIVE' " +
           "AND s.endDate >= CURRENT_DATE")
    fun findActiveSubscriptionByUserId(@Param("userId") userId: Long): SubscriptionEntity?

    fun findByEndDateBeforeAndStatus(endDate: LocalDate, status: SubscriptionEntity.SubscriptionStatus): List<SubscriptionEntity>

    @Query("SELECT s FROM SubscriptionEntity s WHERE s.endDate BETWEEN CURRENT_DATE AND :expirationDate " +
           "AND s.status = 'ACTIVE'")
    fun findExpiringSubscriptions(@Param("expirationDate") expirationDate: LocalDate): List<SubscriptionEntity>

    fun countByStatus(status: SubscriptionEntity.SubscriptionStatus): Long

    @Query("SELECT s FROM SubscriptionEntity s WHERE " +
           "(:query IS NULL OR LOWER(s.user.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.user.fullName) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "AND (:status IS NULL OR s.status = :status)")
    fun searchSubscriptions(
        @Param("query") query: String?,
        @Param("status") status: SubscriptionEntity.SubscriptionStatus?,
        pageable: Pageable
    ): Page<SubscriptionEntity>

    @Query("SELECT COUNT(s) FROM SubscriptionEntity s WHERE s.startDate >= CURRENT_DATE - 30")
    fun countNewSubscriptionsLast30Days(): Long

    @Query("SELECT s.user, COUNT(s) FROM SubscriptionEntity s " +
           "GROUP BY s.user ORDER BY COUNT(s) DESC")
    fun countSubscriptionsByUser(): List<Array<Any>>

    @Query("SELECT AVG(DATEDIFF(s.endDate, s.startDate)) FROM SubscriptionEntity s " +
           "WHERE s.status = 'ACTIVE'")
    fun findAverageSubscriptionDuration(): Double?

    @Query("SELECT s FROM SubscriptionEntity s WHERE s.user = :user " +
           "ORDER BY s.startDate DESC LIMIT 1")
    fun findLatestSubscriptionByUser(@Param("user") user: UserEntity): SubscriptionEntity?
}
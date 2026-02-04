package com.demo.streamflix.repository

import com.demo.streamflix.model.entity.SearchLogEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SearchLogRepository : JpaRepository<SearchLogEntity, Long> {

    @Query("SELECT DISTINCT sl.query FROM SearchLogEntity sl WHERE LOWER(sl.query) LIKE LOWER(CONCAT(:prefix, '%')) ORDER BY sl.createdAt DESC")
    fun findSuggestions(@Param("prefix") prefix: String): List<String>

    @Query("SELECT sl.query, COUNT(sl) as count FROM SearchLogEntity sl WHERE sl.createdAt >= CURRENT_DATE - 30 GROUP BY sl.query ORDER BY count DESC")
    fun findPopularSearchTerms(pageable: Pageable): List<Array<Any>>

    @Query("SELECT sl FROM SearchLogEntity sl WHERE sl.user.id = :userId ORDER BY sl.createdAt DESC")
    fun findByUserId(@Param("userId") userId: Long, pageable: Pageable): List<SearchLogEntity>

    @Query("SELECT AVG(sl.executionTimeMs) FROM SearchLogEntity sl WHERE sl.createdAt >= CURRENT_DATE - 7")
    fun findAverageSearchTimeLast7Days(): Double?

    fun deleteByCreatedAtBefore(date: java.time.LocalDateTime): Long
}
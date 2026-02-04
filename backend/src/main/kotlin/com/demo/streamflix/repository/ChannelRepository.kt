package com.demo.streamflix.repository

import com.demo.streamflix.model.entity.CategoryEntity
import com.demo.streamflix.model.entity.ChannelEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ChannelRepository : JpaRepository<ChannelEntity, Long> {

    fun findByNumber(number: String): ChannelEntity?

    fun existsByNumber(number: String): Boolean

    fun findByIsActiveTrueOrderByNumberAsc(): List<ChannelEntity>

    fun findByCategoryAndIsActiveTrueOrderByNumberAsc(category: CategoryEntity?): List<ChannelEntity>

    fun findByNumberInAndIsActiveTrue(numbers: List<String>): List<ChannelEntity>

    fun countByIsActiveTrue(): Long

    fun countByIsHdTrueAndIsActiveTrue(): Long

    @Query("SELECT c FROM ChannelEntity c WHERE c.isActive = true " +
           "AND (LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "c.number LIKE CONCAT('%', :query, '%'))")
    fun searchChannels(@Param("query") query: String, pageable: Pageable): Page<ChannelEntity>

    @Query("SELECT c FROM ChannelEntity c WHERE c.isActive = true " +
           "AND (:categoryId IS NULL OR c.category.id = :categoryId) " +
           "AND (:isHd IS NULL OR c.isHd = :isHd) " +
           "AND (:query IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    fun advancedSearch(
        @Param("query") query: String?,
        @Param("categoryId") categoryId: Long?,
        @Param("isHd") isHd: Boolean?,
        pageable: Pageable
    ): Page<ChannelEntity>

    @Query("SELECT c FROM ChannelEntity c WHERE c.isActive = true ORDER BY c.viewsCount DESC")
    fun findTopByIsActiveTrueOrderByViewsCountDesc(pageable: Pageable): Page<ChannelEntity>

    @Query("SELECT c FROM ChannelEntity c WHERE c.isActive = true AND c.category IS NULL")
    fun findChannelsWithoutCategory(): List<ChannelEntity>

    @Query("SELECT c FROM ChannelEntity c WHERE c.isActive = true " +
           "AND c.category.slug = :categorySlug " +
           "ORDER BY c.number")
    fun findByCategorySlugAndIsActiveTrue(@Param("categorySlug") categorySlug: String): List<ChannelEntity>

    @Query("SELECT COUNT(c) FROM ChannelEntity c WHERE c.createdAt >= CURRENT_DATE - 30")
    fun countNewChannelsLast30Days(): Long

    @Query("SELECT c.category.name, COUNT(c) FROM ChannelEntity c " +
           "WHERE c.isActive = true AND c.category IS NOT NULL " +
           "GROUP BY c.category.name ORDER BY COUNT(c) DESC")
    fun countChannelsByCategory(): List<Array<Any>>
    
    @Query("SELECT c FROM ChannelEntity c WHERE c.number IN :numbers AND c.isActive = true")
    fun getChannelsByNumbers(@Param("numbers") numbers: List<String>): List<ChannelEntity>
}
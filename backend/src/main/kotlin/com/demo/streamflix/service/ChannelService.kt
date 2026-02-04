package com.demo.streamflix.service

import com.demo.streamflix.model.entity.ChannelEntity
import com.demo.streamflix.model.response.ChannelResponse
import com.demo.streamflix.exception.NotFoundException
import com.demo.streamflix.repository.ChannelRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class ChannelService(
    private val channelRepository: ChannelRepository,
    private val categoryService: CategoryService
) {

    fun getAllChannels(): List<ChannelResponse> {
        return channelRepository.findAll(Sort.by(Sort.Direction.ASC, "number"))
            .filter { it.isActive }
            .map { it.toResponse() }
    }

    fun getActiveChannels(): List<ChannelResponse> {
        return channelRepository.findByIsActiveTrueOrderByNumberAsc()
            .map { it.toResponse() }
    }

    fun getChannelById(id: Long): ChannelResponse {
        val channel = channelRepository.findById(id)
            .orElseThrow { NotFoundException("Canal não encontrado com ID: $id") }
        
        if (!channel.isActive) {
            throw NotFoundException("Canal não está ativo")
        }
        
        return channel.toResponse()
    }

    fun getChannelByNumber(number: String): ChannelResponse {
        // Garantir que o número tenha 3 dígitos
        val formattedNumber = number.padStart(3, '0')
        
        val channel = channelRepository.findByNumber(formattedNumber)
            ?: throw NotFoundException("Canal não encontrado com número: $formattedNumber")
        
        if (!channel.isActive) {
            throw NotFoundException("Canal não está ativo")
        }
        
        return channel.toResponse()
    }

    fun getChannelsByCategory(categoryId: Long): List<ChannelResponse> {
        val category = categoryService.getCategoryEntityById(categoryId)
        return channelRepository.findByCategoryAndIsActiveTrueOrderByNumberAsc(category)
            .map { it.toResponse() }
    }

    fun getChannelsByCategorySlug(slug: String): List<ChannelResponse> {
        val category = categoryService.getCategoryEntityBySlug(slug)
        return channelRepository.findByCategoryAndIsActiveTrueOrderByNumberAsc(category)
            .map { it.toResponse() }
    }

    fun incrementViews(channelId: Long) {
        val channel = channelRepository.findById(channelId)
            .orElseThrow { NotFoundException("Canal não encontrado com ID: $channelId") }
        
        channel.viewsCount += 1
        channel.updatedAt = LocalDateTime.now()
        
        channelRepository.save(channel)
    }

    fun getFeaturedChannels(limit: Int = 10): List<ChannelResponse> {
        return channelRepository.findTopByIsActiveTrueOrderByViewsCountDesc(
            PageRequest.of(0, limit)
        ).map { it.toResponse() }
    }

    fun searchChannels(query: String, page: Int, size: Int): Page<ChannelResponse> {
        val pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "number"))
        return channelRepository.searchChannels(query, pageable)
            .map { it.toResponse() }
    }

    fun createChannel(
        number: String,
        name: String,
        description: String?,
        streamUrl: String,
        logoUrl: String?,
        categoryId: Long?,
        isHd: Boolean = false
    ): ChannelResponse {
        // Verificar se número já existe
        val formattedNumber = number.padStart(3, '0')
        if (channelRepository.existsByNumber(formattedNumber)) {
            throw IllegalArgumentException("Já existe um canal com o número: $formattedNumber")
        }

        val category = categoryId?.let { categoryService.getCategoryEntityById(it) }
        
        val channel = ChannelEntity(
            number = formattedNumber,
            name = name,
            description = description,
            streamUrl = streamUrl,
            logoUrl = logoUrl,
            category = category,
            isHd = isHd,
            isActive = true,
            viewsCount = 0
        )

        val savedChannel = channelRepository.save(channel)
        return savedChannel.toResponse()
    }

    fun updateChannel(
        channelId: Long,
        name: String?,
        description: String?,
        streamUrl: String?,
        logoUrl: String?,
        categoryId: Long?,
        isHd: Boolean?,
        isActive: Boolean?
    ): ChannelResponse {
        val channel = channelRepository.findById(channelId)
            .orElseThrow { NotFoundException("Canal não encontrado com ID: $channelId") }

        name?.let { channel.name = it }
        description?.let { channel.description = it }
        streamUrl?.let { channel.streamUrl = it }
        logoUrl?.let { channel.logoUrl = it }
        isHd?.let { channel.isHd = it }
        isActive?.let { channel.isActive = it }
        
        categoryId?.let {
            val category = categoryService.getCategoryEntityById(it)
            channel.category = category
        }

        channel.updatedAt = LocalDateTime.now()
        val updatedChannel = channelRepository.save(channel)
        
        return updatedChannel.toResponse()
    }

    fun deleteChannel(channelId: Long) {
        if (!channelRepository.existsById(channelId)) {
            throw NotFoundException("Canal não encontrado com ID: $channelId")
        }
        channelRepository.deleteById(channelId)
    }

    fun getChannelsByNumbers(numbers: List<String>): List<ChannelResponse> {
        val formattedNumbers = numbers.map { it.padStart(3, '0') }
        return channelRepository.findByNumberInAndIsActiveTrue(formattedNumbers)
            .sortedBy { it.number }
            .map { it.toResponse() }
    }

    fun getChannelsCount(): Long {
        return channelRepository.countByIsActiveTrue()
    }

    fun getHdChannelsCount(): Long {
        return channelRepository.countByIsHdTrueAndIsActiveTrue()
    }
}
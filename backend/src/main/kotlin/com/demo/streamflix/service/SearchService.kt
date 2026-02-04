package com.demo.streamflix.service

import com.demo.streamflix.model.response.ChannelResponse
import com.demo.streamflix.repository.ChannelRepository
import com.demo.streamflix.repository.SearchLogRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class SearchService(
    private val channelRepository: ChannelRepository,
    private val searchLogRepository: SearchLogRepository
) {

    fun searchChannels(query: String, page: Int, size: Int): List<ChannelResponse> {
        val pageable = PageRequest.of(page, size)
        val results = channelRepository.searchChannels(query, pageable)
        
        // Registrar busca no log
        logSearch(query, results.totalElements)
        
        return results.content.map { it.toResponse() }
    }

    fun advancedSearch(
        query: String?,
        categoryId: Long?,
        isHd: Boolean?,
        page: Int,
        size: Int
    ): Map<String, Any> {
        val pageable = PageRequest.of(page, size)
        val results = channelRepository.advancedSearch(query, categoryId, isHd, pageable)
        
        query?.let { logSearch(it, results.totalElements) }
        
        return mapOf(
            "channels" to results.content.map { it.toResponse() },
            "total" to results.totalElements,
            "page" to page,
            "size" to size,
            "totalPages" to results.totalPages
        )
    }

    fun getSearchSuggestions(prefix: String?): List<String> {
        if (prefix.isNullOrBlank()) {
            return getPopularSearchTerms().map { it["term"] as String }
        }
        
        // Buscar sugestões baseadas em buscas anteriores
        val suggestions = searchLogRepository.findSuggestions(prefix.lowercase())
        
        // Adicionar sugestões baseadas em nomes de canais
        val channelSuggestions = channelRepository.searchChannels(prefix, PageRequest.of(0, 5))
            .content.map { it.name }
        
        return (suggestions + channelSuggestions).distinct().take(10)
    }

    fun getPopularSearchTerms(): List<Map<String, Any>> {
        val popularTerms = searchLogRepository.findPopularSearchTerms(PageRequest.of(0, 10))
        
        return popularTerms.map { termData ->
            val term = termData[0] as String
            val count = termData[1] as Long
            mapOf("term" to term, "count" to count)
        }
    }

    fun getChannelsByNumbers(numbers: List<String>): List<ChannelResponse> {
        return channelRepository.getChannelsByNumbers(numbers)
    }

    private fun logSearch(query: String, resultCount: Long) {
        // Aqui você pode implementar o registro de buscas
        // Por enquanto, vamos apenas imprimir no log
        println("Busca realizada: '$query' - Resultados: $resultCount - ${LocalDateTime.now()}")
        
        // Em uma implementação real, você salvaria no SearchLogRepository
        // searchLogRepository.save(SearchLogEntity(query = query, resultCount = resultCount))
    }

    fun getRecentSearches(userId: Long?, limit: Int = 10): List<String> {
        // Implementar busca de histórico de buscas do usuário
        // Por enquanto, retornar vazio
        return emptyList()
    }
}
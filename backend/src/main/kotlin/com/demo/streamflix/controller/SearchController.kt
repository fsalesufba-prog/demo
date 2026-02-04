package com.demo.streamflix.controller

import com.demo.streamflix.annotation.ValidateSubscription
import com.demo.streamflix.model.response.ApiResponse
import com.demo.streamflix.model.response.ChannelResponse
import com.demo.streamflix.service.SearchService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/search")
@Tag(name = "Busca", description = "Endpoints para busca de canais e conteúdo")
@SecurityRequirement(name = "bearerAuth")
class SearchController(
    private val searchService: SearchService
) {

    @GetMapping("/channels")
    @ValidateSubscription
    @Operation(summary = "Buscar canais", description = "Busca canais por termo de pesquisa")
    fun searchChannels(
        @RequestParam query: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<ApiResponse<List<ChannelResponse>>> {
        val channels = searchService.searchChannels(query, page, size)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = channels,
                message = "Resultados da busca por '$query'"
            )
        )
    }

    @GetMapping("/channels/advanced")
    @ValidateSubscription
    @Operation(summary = "Busca avançada", description = "Busca avançada de canais com múltiplos filtros")
    fun advancedSearch(
        @RequestParam(required = false) query: String?,
        @RequestParam(required = false) categoryId: Long?,
        @RequestParam(required = false) isHd: Boolean?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<ApiResponse<Map<String, Any>>> {
        val result = searchService.advancedSearch(query, categoryId, isHd, page, size)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = result,
                message = "Resultados da busca avançada"
            )
        )
    }

    @GetMapping("/suggestions")
    @ValidateSubscription
    @Operation(summary = "Sugestões de busca", description = "Retorna sugestões de busca baseadas no histórico")
    fun getSearchSuggestions(
        @RequestParam(required = false) prefix: String?
    ): ResponseEntity<ApiResponse<List<String>>> {
        val suggestions = searchService.getSearchSuggestions(prefix)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = suggestions,
                message = "Sugestões de busca"
            )
        )
    }

    @GetMapping("/popular")
    @ValidateSubscription
    @Operation(summary = "Termos populares", description = "Retorna termos de busca mais populares")
    fun getPopularSearchTerms(): ResponseEntity<ApiResponse<List<Map<String, Any>>>> {
        val popularTerms = searchService.getPopularSearchTerms()
        return ResponseEntity.ok(
            ApiResponse.success(
                data = popularTerms,
                message = "Termos de busca populares"
            )
        )
    }

    @GetMapping("/channels/by-numbers")
    @ValidateSubscription
    @Operation(summary = "Buscar por números", description = "Busca canais por números específicos (ex: 001, 002)")
    fun getChannelsByNumbers(
        @RequestParam numbers: List<String>
    ): ResponseEntity<ApiResponse<List<ChannelResponse>>> {
        val channels = searchService.getChannelsByNumbers(numbers)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = channels,
                message = "Canais encontrados pelos números"
            )
        )
    }
}
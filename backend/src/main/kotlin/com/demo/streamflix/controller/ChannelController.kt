package com.demo.streamflix.controller

import com.demo.streamflix.annotation.ValidateSubscription
import com.demo.streamflix.model.response.ApiResponse
import com.demo.streamflix.model.response.ChannelResponse
import com.demo.streamflix.service.ChannelService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/channels")
@Tag(name = "Canais", description = "Endpoints para gerenciamento e visualização de canais")
@SecurityRequirement(name = "bearerAuth")
class ChannelController(
    private val channelService: ChannelService
) {

    @GetMapping
    @ValidateSubscription
    @Operation(summary = "Listar todos os canais", description = "Retorna lista de todos os canais disponíveis")
    fun getAllChannels(): ResponseEntity<ApiResponse<List<ChannelResponse>>> {
        val channels = channelService.getAllChannels()
        return ResponseEntity.ok(
            ApiResponse.success(
                data = channels,
                message = "Canais recuperados com sucesso"
            )
        )
    }

    @GetMapping("/public")
    @Operation(summary = "Listar canais públicos", description = "Retorna lista de canais (sem necessidade de autenticação)")
    fun getPublicChannels(): ResponseEntity<ApiResponse<List<ChannelResponse>>> {
        val channels = channelService.getActiveChannels()
        return ResponseEntity.ok(
            ApiResponse.success(
                data = channels,
                message = "Canais públicos recuperados"
            )
        )
    }

    @GetMapping("/{id}")
    @ValidateSubscription
    @Operation(summary = "Buscar canal por ID", description = "Retorna detalhes de um canal específico")
    fun getChannelById(@PathVariable id: Long): ResponseEntity<ApiResponse<ChannelResponse>> {
        val channel = channelService.getChannelById(id)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = channel,
                message = "Canal encontrado"
            )
        )
    }

    @GetMapping("/number/{number}")
    @ValidateSubscription
    @Operation(summary = "Buscar canal por número", description = "Retorna canal pelo seu número (ex: 001)")
    fun getChannelByNumber(@PathVariable number: String): ResponseEntity<ApiResponse<ChannelResponse>> {
        val channel = channelService.getChannelByNumber(number)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = channel,
                message = "Canal encontrado"
            )
        )
    }

    @GetMapping("/category/{categoryId}")
    @ValidateSubscription
    @Operation(summary = "Listar canais por categoria", description = "Retorna canais de uma categoria específica")
    fun getChannelsByCategory(@PathVariable categoryId: Long): ResponseEntity<ApiResponse<List<ChannelResponse>>> {
        val channels = channelService.getChannelsByCategory(categoryId)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = channels,
                message = "Canais da categoria recuperados"
            )
        )
    }

    @GetMapping("/category/slug/{slug}")
    @ValidateSubscription
    @Operation(summary = "Listar canais por slug da categoria", description = "Retorna canais por slug da categoria")
    fun getChannelsByCategorySlug(@PathVariable slug: String): ResponseEntity<ApiResponse<List<ChannelResponse>>> {
        val channels = channelService.getChannelsByCategorySlug(slug)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = channels,
                message = "Canais da categoria recuperados"
            )
        )
    }

    @PostMapping("/{id}/view")
    @ValidateSubscription
    @Operation(summary = "Registrar visualização", description = "Incrementa contador de visualizações do canal")
    fun registerView(@PathVariable id: Long): ResponseEntity<ApiResponse<Void>> {
        channelService.incrementViews(id)
        return ResponseEntity.ok(
            ApiResponse.success(
                message = "Visualização registrada"
            )
        )
    }

    @GetMapping("/featured")
    @ValidateSubscription
    @Operation(summary = "Canais em destaque", description = "Retorna canais mais populares ou em destaque")
    fun getFeaturedChannels(): ResponseEntity<ApiResponse<List<ChannelResponse>>> {
        val channels = channelService.getFeaturedChannels()
        return ResponseEntity.ok(
            ApiResponse.success(
                data = channels,
                message = "Canais em destaque recuperados"
            )
        )
    }
}
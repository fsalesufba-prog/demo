package com.demo.streamflix.controller

import com.demo.streamflix.annotation.AdminOnly
import com.demo.streamflix.model.request.SubscriptionRequest
import com.demo.streamflix.model.response.ApiResponse
import com.demo.streamflix.model.dto.SubscriptionDto
import com.demo.streamflix.service.SubscriptionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/subscriptions")
@Tag(name = "Assinaturas", description = "Endpoints para gerenciamento de assinaturas/membresías")
@SecurityRequirement(name = "bearerAuth")
class SubscriptionController(
    private val subscriptionService: SubscriptionService
) {

    @PostMapping
    @AdminOnly
    @Operation(summary = "Criar assinatura", description = "Cria uma nova assinatura para um usuário (apenas admin)")
    fun createSubscription(
        @Valid @RequestBody request: SubscriptionRequest
    ): ResponseEntity<ApiResponse<SubscriptionDto>> {
        val subscription = subscriptionService.createSubscription(request)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = subscription,
                message = "Assinatura criada com sucesso"
            )
        )
    }

    @GetMapping("/user/{userId}")
    @AdminOnly
    @Operation(summary = "Assinaturas do usuário", description = "Retorna assinaturas de um usuário específico")
    fun getUserSubscriptions(@PathVariable userId: Long): ResponseEntity<ApiResponse<List<SubscriptionDto>>> {
        val subscriptions = subscriptionService.getUserSubscriptions(userId)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = subscriptions,
                message = "Assinaturas do usuário recuperadas"
            )
        )
    }

    @GetMapping("/my-subscriptions")
    @Operation(summary = "Minhas assinaturas", description = "Retorna assinaturas do usuário autenticado")
    fun getMySubscriptions(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<ApiResponse<List<SubscriptionDto>>> {
        val subscriptions = subscriptionService.getUserSubscriptionsByEmail(userDetails.username)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = subscriptions,
                message = "Suas assinaturas recuperadas"
            )
        )
    }

    @GetMapping("/active")
    @Operation(summary = "Assinatura ativa", description = "Retorna a assinatura ativa do usuário autenticado")
    fun getActiveSubscription(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<ApiResponse<SubscriptionDto?>> {
        val subscription = subscriptionService.getActiveSubscription(userDetails.username)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = subscription,
                message = "Assinatura ativa recuperada"
            )
        )
    }

    @PatchMapping("/{subscriptionId}/extend")
    @AdminOnly
    @Operation(summary = "Estender assinatura", description = "Estende a duração de uma assinatura")
    fun extendSubscription(
        @PathVariable subscriptionId: Long,
        @RequestParam days: Int
    ): ResponseEntity<ApiResponse<SubscriptionDto>> {
        val subscription = subscriptionService.extendSubscription(subscriptionId, days)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = subscription,
                message = "Assinatura estendida com sucesso"
            )
        )
    }

    @PatchMapping("/{subscriptionId}/cancel")
    @AdminOnly
    @Operation(summary = "Cancelar assinatura", description = "Cancela uma assinatura existente")
    fun cancelSubscription(@PathVariable subscriptionId: Long): ResponseEntity<ApiResponse<SubscriptionDto>> {
        val subscription = subscriptionService.cancelSubscription(subscriptionId)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = subscription,
                message = "Assinatura cancelada com sucesso"
            )
        )
    }

    @GetMapping("/expiring-soon")
    @AdminOnly
    @Operation(summary = "Assinaturas prestes a expirar", description = "Lista assinaturas que expiram em breve")
    fun getExpiringSubscriptions(
        @RequestParam(defaultValue = "7") days: Int
    ): ResponseEntity<ApiResponse<List<SubscriptionDto>>> {
        val subscriptions = subscriptionService.getExpiringSubscriptions(days)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = subscriptions,
                message = "Assinaturas prestes a expirar"
            )
        )
    }

    @PostMapping("/validate")
    @Operation(summary = "Validar assinatura", description = "Valida se o usuário tem uma assinatura ativa")
    fun validateSubscription(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<ApiResponse<Map<String, Any>>> {
        val isValid = subscriptionService.validateUserSubscription(userDetails.username)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = mapOf("valid" to isValid),
                message = if (isValid) "Assinatura válida" else "Assinatura inválida ou expirada"
            )
        )
    }
}
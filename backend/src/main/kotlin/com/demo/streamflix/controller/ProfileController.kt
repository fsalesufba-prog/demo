package com.demo.streamflix.controller

import com.demo.streamflix.model.request.UpdateProfileRequest
import com.demo.streamflix.model.response.ApiResponse
import com.demo.streamflix.model.dto.UserDto
import com.demo.streamflix.service.ProfileService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profile")
@Tag(name = "Perfil", description = "Endpoints para gerenciamento do perfil do usuário")
@SecurityRequirement(name = "bearerAuth")
class ProfileController(
    private val profileService: ProfileService
) {

    @GetMapping
    @Operation(summary = "Obter perfil", description = "Retorna os dados do perfil do usuário autenticado")
    fun getProfile(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<ApiResponse<UserDto>> {
        val user = profileService.getProfile(userDetails.username)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = user,
                message = "Perfil recuperado com sucesso"
            )
        )
    }

    @PutMapping
    @Operation(summary = "Atualizar perfil", description = "Atualiza os dados do perfil do usuário")
    fun updateProfile(
        @AuthenticationPrincipal userDetails: UserDetails,
        @Valid @RequestBody request: UpdateProfileRequest
    ): ResponseEntity<ApiResponse<UserDto>> {
        val updatedUser = profileService.updateProfile(userDetails.username, request)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = updatedUser,
                message = "Perfil atualizado com sucesso"
            )
        )
    }

    @PostMapping("/change-password")
    @Operation(summary = "Alterar senha", description = "Altera a senha do usuário")
    fun changePassword(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestBody request: Map<String, String>
    ): ResponseEntity<ApiResponse<Void>> {
        val currentPassword = request["currentPassword"] ?: throw IllegalArgumentException("Senha atual é obrigatória")
        val newPassword = request["newPassword"] ?: throw IllegalArgumentException("Nova senha é obrigatória")
        
        profileService.changePassword(userDetails.username, currentPassword, newPassword)
        
        return ResponseEntity.ok(
            ApiResponse.success(
                message = "Senha alterada com sucesso"
            )
        )
    }

    @GetMapping("/subscription-status")
    @Operation(summary = "Status da assinatura", description = "Verifica o status da assinatura/membresía do usuário")
    fun getSubscriptionStatus(
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<ApiResponse<Map<String, Any>>> {
        val status = profileService.getSubscriptionStatus(userDetails.username)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = status,
                message = "Status da assinatura recuperado"
            )
        )
    }
}
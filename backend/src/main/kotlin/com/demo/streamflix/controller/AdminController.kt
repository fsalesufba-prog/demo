package com.demo.streamflix.controller

import com.demo.streamflix.annotation.AdminOnly
import com.demo.streamflix.model.response.ApiResponse
import com.demo.streamflix.model.response.DashboardStats
import com.demo.streamflix.service.AdminService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Administração", description = "Endpoints administrativos do sistema")
@SecurityRequirement(name = "bearerAuth")
class AdminController(
    private val adminService: AdminService
) {

    @GetMapping("/dashboard")
    @AdminOnly
    @Operation(summary = "Dashboard", description = "Retorna estatísticas do sistema para o painel admin")
    fun getDashboardStats(): ResponseEntity<ApiResponse<DashboardStats>> {
        val stats = adminService.getDashboardStats()
        return ResponseEntity.ok(
            ApiResponse.success(
                data = stats,
                message = "Estatísticas do dashboard recuperadas"
            )
        )
    }

    @PostMapping("/users/{userId}/impersonate")
    @AdminOnly
    @Operation(summary = "Personificar usuário", description = "Gera token para acessar como outro usuário")
    fun impersonateUser(@PathVariable userId: Long): ResponseEntity<ApiResponse<Map<String, String>>> {
        val token = adminService.generateImpersonationToken(userId)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = mapOf("token" to token),
                message = "Token de personificação gerado"
            )
        )
    }

    @PostMapping("/channels/{channelId}/feature")
    @AdminOnly
    @Operation(summary = "Destacar canal", description = "Adiciona ou remove canal da lista de destaques")
    fun toggleChannelFeatured(@PathVariable channelId: Long): ResponseEntity<ApiResponse<Void>> {
        adminService.toggleChannelFeatured(channelId)
        return ResponseEntity.ok(
            ApiResponse.success(
                message = "Status de destaque do canal alterado"
            )
        )
    }

    @PostMapping("/users/{userId}/create-admin")
    @AdminOnly
    @Operation(summary = "Criar admin", description = "Converte um usuário normal em administrador")
    fun makeUserAdmin(@PathVariable userId: Long): ResponseEntity<ApiResponse<Void>> {
        adminService.makeUserAdmin(userId)
        return ResponseEntity.ok(
            ApiResponse.success(
                message = "Usuário promovido a administrador"
            )
        )
    }

    @GetMapping("/logs/access")
    @AdminOnly
    @Operation(summary = "Logs de acesso", description = "Retorna logs de acesso do sistema")
    fun getAccessLogs(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "50") size: Int
    ): ResponseEntity<ApiResponse<Map<String, Any>>> {
        val logs = adminService.getAccessLogs(page, size)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = logs,
                message = "Logs de acesso recuperados"
            )
        )
    }
}
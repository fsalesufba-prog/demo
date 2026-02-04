package com.demo.streamflix.controller

import com.demo.streamflix.annotation.AdminOnly
import com.demo.streamflix.model.request.UpdateProfileRequest
import com.demo.streamflix.model.response.ApiResponse
import com.demo.streamflix.model.dto.UserDto
import com.demo.streamflix.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
@SecurityRequirement(name = "bearerAuth")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    @AdminOnly
    @Operation(summary = "Listar todos os usuários", description = "Retorna lista de todos os usuários (apenas admin)")
    fun getAllUsers(): ResponseEntity<ApiResponse<List<UserDto>>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(
            ApiResponse.success(
                data = users,
                message = "Usuários recuperados com sucesso"
            )
        )
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna detalhes de um usuário específico")
    fun getUserById(@PathVariable id: Long): ResponseEntity<ApiResponse<UserDto>> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = user,
                message = "Usuário encontrado"
            )
        )
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário")
    fun updateUser(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateProfileRequest
    ): ResponseEntity<ApiResponse<UserDto>> {
        val updatedUser = userService.updateUser(id, request)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = updatedUser,
                message = "Usuário atualizado com sucesso"
            )
        )
    }

    @DeleteMapping("/{id}")
    @AdminOnly
    @Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema (apenas admin)")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<ApiResponse<Void>> {
        userService.deleteUser(id)
        return ResponseEntity.ok(
            ApiResponse.success(
                message = "Usuário deletado com sucesso"
            )
        )
    }

    @PatchMapping("/{id}/toggle-active")
    @AdminOnly
    @Operation(summary = "Ativar/Desativar usuário", description = "Alterna o status ativo de um usuário")
    fun toggleUserActive(@PathVariable id: Long): ResponseEntity<ApiResponse<UserDto>> {
        val user = userService.toggleUserActive(id)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = user,
                message = if (user.isActive) "Usuário ativado" else "Usuário desativado"
            )
        )
    }
}
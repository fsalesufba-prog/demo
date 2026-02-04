package com.demo.streamflix.controller

import com.demo.streamflix.model.request.LoginRequest
import com.demo.streamflix.model.response.ApiResponse
import com.demo.streamflix.model.response.AuthResponse
import com.demo.streamflix.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints para autenticação de usuários")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Autentica um usuário e retorna token JWT")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<ApiResponse<AuthResponse>> {
        val authResponse = authService.authenticate(request.email, request.password)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = authResponse,
                message = "Login realizado com sucesso"
            )
        )
    }

    @PostMapping("/validate-token")
    @Operation(summary = "Validar token", description = "Valida se um token JWT é válido")
    fun validateToken(): ResponseEntity<ApiResponse<Map<String, Any>>> {
        return ResponseEntity.ok(
            ApiResponse.success(
                data = mapOf("valid" to true),
                message = "Token válido"
            )
        )
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Invalida o token atual")
    fun logout(): ResponseEntity<ApiResponse<Void>> {
        // Em uma implementação real, você pode adicionar o token a uma blacklist
        return ResponseEntity.ok(
            ApiResponse.success(
                message = "Logout realizado com sucesso"
            )
        )
    }
}
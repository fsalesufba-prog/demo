package com.demo.streamflix.controller

import com.demo.streamflix.model.response.ApiResponse
import com.demo.streamflix.model.response.CategoryResponse
import com.demo.streamflix.service.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias de canais")
@SecurityRequirement(name = "bearerAuth")
class CategoryController(
    private val categoryService: CategoryService
) {

    @GetMapping
    @Operation(summary = "Listar todas as categorias", description = "Retorna lista de todas as categorias")
    fun getAllCategories(): ResponseEntity<ApiResponse<List<CategoryResponse>>> {
        val categories = categoryService.getAllCategories()
        return ResponseEntity.ok(
            ApiResponse.success(
                data = categories,
                message = "Categorias recuperadas com sucesso"
            )
        )
    }

    @GetMapping("/public")
    @Operation(summary = "Listar categorias públicas", description = "Retorna categorias ativas (sem autenticação)")
    fun getPublicCategories(): ResponseEntity<ApiResponse<List<CategoryResponse>>> {
        val categories = categoryService.getActiveCategories()
        return ResponseEntity.ok(
            ApiResponse.success(
                data = categories,
                message = "Categorias públicas recuperadas"
            )
        )
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Retorna detalhes de uma categoria")
    fun getCategoryById(@PathVariable id: Long): ResponseEntity<ApiResponse<CategoryResponse>> {
        val category = categoryService.getCategoryById(id)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = category,
                message = "Categoria encontrada"
            )
        )
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Buscar categoria por slug", description = "Retorna categoria pelo seu slug")
    fun getCategoryBySlug(@PathVariable slug: String): ResponseEntity<ApiResponse<CategoryResponse>> {
        val category = categoryService.getCategoryBySlug(slug)
        return ResponseEntity.ok(
            ApiResponse.success(
                data = category,
                message = "Categoria encontrada"
            )
        )
    }

    @GetMapping("/with-channels")
    @Operation(summary = "Categorias com canais", description = "Retorna categorias com seus canais incluídos")
    fun getCategoriesWithChannels(): ResponseEntity<ApiResponse<List<CategoryResponse>>> {
        val categories = categoryService.getCategoriesWithChannels()
        return ResponseEntity.ok(
            ApiResponse.success(
                data = categories,
                message = "Categorias com canais recuperadas"
            )
        )
    }
}
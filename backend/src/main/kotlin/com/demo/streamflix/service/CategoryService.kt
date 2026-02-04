package com.demo.streamflix.service

import com.demo.streamflix.model.entity.CategoryEntity
import com.demo.streamflix.model.response.CategoryResponse
import com.demo.streamflix.exception.NotFoundException
import com.demo.streamflix.repository.CategoryRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val channelService: ChannelService
) {

    fun getAllCategories(): List<CategoryResponse> {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "sortOrder"))
            .map { it.toResponse() }
    }

    fun getActiveCategories(): List<CategoryResponse> {
        return categoryRepository.findByIsActiveTrueOrderBySortOrderAsc()
            .map { it.toResponse() }
    }

    fun getCategoryById(id: Long): CategoryResponse {
        val category = getCategoryEntityById(id)
        return category.toResponse()
    }

    fun getCategoryBySlug(slug: String): CategoryResponse {
        val category = getCategoryEntityBySlug(slug)
        return category.toResponse()
    }

    fun getCategoryEntityById(id: Long): CategoryEntity {
        return categoryRepository.findById(id)
            .orElseThrow { NotFoundException("Categoria não encontrada com ID: $id") }
    }

    fun getCategoryEntityBySlug(slug: String): CategoryEntity {
        return categoryRepository.findBySlug(slug)
            ?: throw NotFoundException("Categoria não encontrada com slug: $slug")
    }

    fun getCategoriesWithChannels(): List<CategoryResponse> {
        val categories = categoryRepository.findByIsActiveTrueOrderBySortOrderAsc()
        
        return categories.map { category ->
            val channels = channelService.getChannelsByCategory(category.id!!)
            category.toResponse().copy(channels = channels)
        }
    }

    fun createCategory(
        name: String,
        slug: String,
        description: String? = null,
        iconUrl: String? = null,
        sortOrder: Int = 0
    ): CategoryResponse {
        // Verificar se slug já existe
        if (categoryRepository.existsBySlug(slug)) {
            throw IllegalArgumentException("Já existe uma categoria com o slug: $slug")
        }

        val category = CategoryEntity(
            name = name,
            slug = slug,
            description = description,
            iconUrl = iconUrl,
            sortOrder = sortOrder,
            isActive = true
        )

        val savedCategory = categoryRepository.save(category)
        return savedCategory.toResponse()
    }

    fun updateCategory(
        categoryId: Long,
        name: String?,
        slug: String?,
        description: String?,
        iconUrl: String?,
        sortOrder: Int?,
        isActive: Boolean?
    ): CategoryResponse {
        val category = getCategoryEntityById(categoryId)

        name?.let { category.name = it }
        description?.let { category.description = it }
        iconUrl?.let { category.iconUrl = it }
        sortOrder?.let { category.sortOrder = it }
        isActive?.let { category.isActive = it }

        // Verificar se novo slug é único
        slug?.let { newSlug ->
            if (newSlug != category.slug) {
                if (categoryRepository.existsBySlug(newSlug)) {
                    throw IllegalArgumentException("Já existe uma categoria com o slug: $newSlug")
                }
                category.slug = newSlug
            }
        }

        val updatedCategory = categoryRepository.save(category)
        return updatedCategory.toResponse()
    }

    fun deleteCategory(categoryId: Long) {
        val category = getCategoryEntityById(categoryId)
        
        // Verificar se categoria tem canais associados
        val channelCount = channelService.getChannelsByCategory(categoryId).size
        if (channelCount > 0) {
            throw IllegalStateException("Não é possível deletar categoria com canais associados. Movimente os canais primeiro.")
        }
        
        categoryRepository.delete(category)
    }

    fun getCategoriesCount(): Long {
        return categoryRepository.countByIsActiveTrue()
    }

    fun getCategoryNames(): List<String> {
        return categoryRepository.findByIsActiveTrueOrderBySortOrderAsc()
            .map { it.name }
    }

    fun initializeDefaultCategories() {
        val defaultCategories = listOf(
            "Nacional" to "nacional",
            "Actualidad" to "actualidad", 
            "Infantil" to "infantil",
            "Regional" to "regional"
        )

        defaultCategories.forEachIndexed { index, (name, slug) ->
            if (!categoryRepository.existsBySlug(slug)) {
                createCategory(
                    name = name,
                    slug = slug,
                    description = "Categoria de $name",
                    sortOrder = index
                )
            }
        }
    }
}
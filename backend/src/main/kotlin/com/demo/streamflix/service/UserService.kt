package com.demo.streamflix.service

import com.demo.streamflix.model.dto.UserDto
import com.demo.streamflix.model.entity.UserEntity
import com.demo.streamflix.model.request.UpdateProfileRequest
import com.demo.streamflix.exception.NotFoundException
import com.demo.streamflix.exception.ValidationException
import com.demo.streamflix.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getAllUsers(): List<UserDto> {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "fullName"))
            .map { it.toDto() }
    }

    fun getUsersPage(page: Int, size: Int): Page<UserDto> {
        val pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "fullName"))
        return userRepository.findAll(pageable).map { it.toDto() }
    }

    fun getUserById(id: Long): UserDto {
        val user = userRepository.findById(id)
            .orElseThrow { NotFoundException("Usuário não encontrado com ID: $id") }
        return user.toDto()
    }

    fun getUserByEmail(email: String): UserDto {
        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException("Usuário não encontrado com email: $email")
        return user.toDto()
    }

    fun updateUser(id: Long, request: UpdateProfileRequest): UserDto {
        val user = userRepository.findById(id)
            .orElseThrow { NotFoundException("Usuário não encontrado com ID: $id") }

        // Atualizar campos básicos
        user.fullName = request.fullName ?: user.fullName
        user.phone = request.phone ?: user.phone
        
        // Se email foi fornecido e é diferente, verificar se já existe
        request.email?.let { newEmail ->
            if (newEmail != user.email) {
                if (userRepository.existsByEmail(newEmail)) {
                    throw ValidationException("Email já está em uso: $newEmail")
                }
                user.email = newEmail
            }
        }

        val updatedUser = userRepository.save(user)
        return updatedUser.toDto()
    }

    fun deleteUser(id: Long) {
        if (!userRepository.existsById(id)) {
            throw NotFoundException("Usuário não encontrado com ID: $id")
        }
        userRepository.deleteById(id)
    }

    fun toggleUserActive(id: Long): UserDto {
        val user = userRepository.findById(id)
            .orElseThrow { NotFoundException("Usuário não encontrado com ID: $id") }
        
        user.isActive = !user.isActive
        user.updatedAt = LocalDateTime.now()
        
        val updatedUser = userRepository.save(user)
        return updatedUser.toDto()
    }

    fun changePassword(userId: Long, currentPassword: String, newPassword: String) {
        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("Usuário não encontrado com ID: $userId") }

        // Verificar senha atual
        if (!passwordEncoder.matches(currentPassword, user.password)) {
            throw ValidationException("Senha atual incorreta")
        }

        // Validar nova senha
        if (newPassword.length < 6) {
            throw ValidationException("A nova senha deve ter pelo menos 6 caracteres")
        }

        // Atualizar senha
        user.password = passwordEncoder.encode(newPassword)
        user.updatedAt = LocalDateTime.now()
        
        userRepository.save(user)
    }

    fun createUser(email: String, password: String, fullName: String, isAdmin: Boolean = false): UserDto {
        // Verificar se email já existe
        if (userRepository.existsByEmail(email)) {
            throw ValidationException("Email já está em uso: $email")
        }

        val user = UserEntity(
            email = email,
            password = passwordEncoder.encode(password),
            fullName = fullName,
            isAdmin = isAdmin,
            isActive = true
        )

        val savedUser = userRepository.save(user)
        return savedUser.toDto()
    }

    fun searchUsers(query: String, page: Int, size: Int): Page<UserDto> {
        val pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "fullName"))
        return userRepository.searchUsers(query, pageable).map { it.toDto() }
    }

    fun getActiveUsersCount(): Long {
        return userRepository.countByIsActive(true)
    }

    fun getAdminsCount(): Long {
        return userRepository.countByIsAdmin(true)
    }
}
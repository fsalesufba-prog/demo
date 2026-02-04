package com.demo.streamflix.model.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

data class UpdateProfileRequest(
    val fullName: String? = null,

    @field:Email(message = "Email inválido")
    val email: String? = null,

    @field:Size(min = 10, max = 20, message = "Telefone deve ter entre 10 e 20 caracteres")
    val phone: String? = null
)
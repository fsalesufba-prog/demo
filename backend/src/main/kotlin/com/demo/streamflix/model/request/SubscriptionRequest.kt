package com.demo.streamflix.model.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class SubscriptionRequest(
    @field:NotNull(message = "ID do usuário é obrigatório")
    val userId: Long,

    val startDate: LocalDate? = null,

    @field:NotNull(message = "Duração é obrigatória")
    @field:Min(value = 1, message = "Duração deve ser pelo menos 1 dia")
    val durationDays: Int = 30
)
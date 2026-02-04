package com.demo.streamflix.model.response

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String,
    val timestamp: String = java.time.Instant.now().toString()
) {
    companion object {
        fun <T> success(data: T? = null, message: String = "Operação realizada com sucesso"): ApiResponse<T> {
            return ApiResponse(true, data, message)
        }

        fun <T> error(message: String, data: T? = null): ApiResponse<T> {
            return ApiResponse(false, data, message)
        }
    }
}
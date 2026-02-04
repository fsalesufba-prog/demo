package com.demo.streamflix.model.response

data class ValidationResponse(
    val valid: Boolean,
    val message: String,
    val details: Map<String, Any>? = null
)
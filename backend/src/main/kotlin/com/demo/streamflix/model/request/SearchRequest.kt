package com.demo.streamflix.model.request

data class SearchRequest(
    val query: String,
    val categoryId: Long? = null,
    val isHd: Boolean? = null,
    val page: Int = 0,
    val size: Int = 20
)
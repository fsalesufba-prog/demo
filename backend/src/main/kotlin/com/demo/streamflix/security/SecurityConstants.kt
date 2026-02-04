package com.demo.streamflix.security

object SecurityConstants {
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
    const val SIGN_UP_URL = "/api/auth/**"
    const val LOGIN_URL = "/api/auth/login"
    const val PUBLIC_URLS = arrayOf(
        "/api/auth/**",
        "/api/health",
        "/api/info",
        "/api/channels/public/**",
        "/api/categories/public/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/api-docs/**",
        "/webjars/**",
        "/static/**"
    )
}
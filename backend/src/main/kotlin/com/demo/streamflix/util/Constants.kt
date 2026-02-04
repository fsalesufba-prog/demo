package com.demo.streamflix.util

object Constants {
    
    // Roles
    const val ROLE_USER = "ROLE_USER"
    const val ROLE_ADMIN = "ROLE_ADMIN"
    
    // Cache
    const val CACHE_CHANNELS = "channels"
    const val CACHE_CATEGORIES = "categories"
    const val CACHE_USERS = "users"
    const val CACHE_TTL = 300L // 5 minutos
    
    // Paginação
    const val DEFAULT_PAGE_SIZE = 20
    const val MAX_PAGE_SIZE = 100
    
    // Validação
    const val MIN_PASSWORD_LENGTH = 6
    const val MAX_PASSWORD_LENGTH = 100
    
    // URLs
    const val DEFAULT_CHANNEL_LOGO = "/static/images/logos/default.png"
    const val DEFAULT_CATEGORY_ICON = "/static/images/icons/default.png"
    
    // Formato de datas
    const val DATE_FORMAT = "yyyy-MM-dd"
    const val DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    
    // Stream
    const val STREAM_TIMEOUT_SECONDS = 30L
    const val MAX_STREAM_RETRIES = 3
    
    // Membresía
    const val SUBSCRIPTION_EXPIRY_WARNING_DAYS = 7
    const val DEFAULT_SUBSCRIPTION_DAYS = 30
    
    // Logging
    const val LOGGING_USER_ACTION = "USER_ACTION"
    const val LOGGING_SYSTEM_EVENT = "SYSTEM_EVENT"
    const val LOGGING_SECURITY = "SECURITY"
}
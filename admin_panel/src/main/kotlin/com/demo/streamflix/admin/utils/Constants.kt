package com.demo.streamflix.admin.utils

object Constants {
    // API Configuration
    const val API_BASE_URL = "http://localhost:8080"
    
    // Local Storage Keys
    const val TOKEN_KEY = "admin_token"
    const val USER_KEY = "admin_user"
    
    // Pagination
    const val ITEMS_PER_PAGE = 10
    
    // Date Formats
    const val DATE_FORMAT = "dd/MM/yyyy HH:mm"
    const val DATE_ONLY_FORMAT = "dd/MM/yyyy"
    
    // Validation
    const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$"
    const val PASSWORD_MIN_LENGTH = 6
    const val USERNAME_MIN_LENGTH = 3
    const val USERNAME_MAX_LENGTH = 50
}
package com.demo.streamflix.util

object Constants {
    const val DEBUG = true
    
    // API Base URL - Update with your actual backend URL
    const val BASE_URL = "http://10.0.2.2:8080/" // For emulator
    
    // Shared Preferences Keys
    const val PREF_IS_LOGGED_IN = "is_logged_in"
    const val PREF_AUTH_TOKEN = "auth_token"
    const val PREF_USER_EMAIL = "user_email"
    const val PREF_MEMBERSHIP_EXPIRY = "membership_expiry"
    const val PREF_REMEMBER_ME = "remember_me"
    
    // Database
    const val DATABASE_NAME = "streamflix_db"
    
    // Cache durations
    const val CACHE_DURATION_SHORT = 5 * 60 * 1000L // 5 minutes
    const val CACHE_DURATION_LONG = 30 * 60 * 1000L // 30 minutes
    
    // Player
    const val EXOPLAYER_USER_AGENT = "StreamFlix-Android"
}
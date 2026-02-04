package com.demo.streamflix.admin.utils

object Validators {
    fun isValidEmail(email: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(email)
    }
    
    fun isValidPassword(password: String): Boolean {
        return password.length >= PASSWORD_MIN_LENGTH
    }
    
    fun isValidUsername(username: String): Boolean {
        return username.length in USERNAME_MIN_LENGTH..USERNAME_MAX_LENGTH &&
               username.matches("^[a-zA-Z0-9_.-]*$".toRegex())
    }
    
    fun validateChannelUrl(url: String): Boolean {
        return url.startsWith("http://") || url.startsWith("https://") ||
               url.startsWith("rtmp://") || url.startsWith("rtsp://")
    }
}
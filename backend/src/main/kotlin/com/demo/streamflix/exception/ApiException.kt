package com.demo.streamflix.exception

import org.springframework.http.HttpStatus

open class ApiException(
    message: String,
    val status: HttpStatus = HttpStatus.BAD_REQUEST
) : RuntimeException(message)

class NotFoundException(message: String) : ApiException(message, HttpStatus.NOT_FOUND)

class AuthException(message: String) : ApiException(message, HttpStatus.UNAUTHORIZED)

class ValidationException(message: String) : ApiException(message, HttpStatus.BAD_REQUEST)

class AccessDeniedException(message: String = "Acesso negado") : ApiException(message, HttpStatus.FORBIDDEN)

class ConflictException(message: String) : ApiException(message, HttpStatus.CONFLICT)
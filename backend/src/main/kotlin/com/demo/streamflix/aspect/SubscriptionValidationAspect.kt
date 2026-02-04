package com.demo.streamflix.aspect

import com.demo.streamflix.annotation.ValidateSubscription
import com.demo.streamflix.exception.AuthException
import com.demo.streamflix.service.ValidationService
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class SubscriptionValidationAspect(
    private val validationService: ValidationService
) {

    @Before("@annotation(validateSubscription)")
    fun validateSubscription(validateSubscription: ValidateSubscription) {
        if (!validationService.validateMembership()) {
            throw AuthException("Assinatura necessária para acessar este recurso")
        }
    }
}
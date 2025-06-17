package com.odensala.hashtalk.auth.domain.usecase

import javax.inject.Inject

class ValidateEmailUseCase
    @Inject
    constructor() {
        operator fun invoke(email: String): ValidationResult {
            if (email.isBlank()) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "Email cannot be empty",
                )
            }

            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (!email.matches(emailPattern.toRegex())) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "Please enter a valid email address",
                )
            }

            return ValidationResult(successful = true)
        }
    }
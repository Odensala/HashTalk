package com.odensala.hashtalk.auth.domain.usecase

import javax.inject.Inject

class ValidatePasswordUseCase
    @Inject
    constructor() {
        operator fun invoke(password: String): ValidationResult {
            if (password.length < 8) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "Password must be at least 8 characters long",
                )
            }

            val containsLettersAndDigits =
                password.any { it.isDigit() } &&
                    password.any { it.isLetter() }
            if (!containsLettersAndDigits) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "Password must contain at least one letter and one digit",
                )
            }

            return ValidationResult(successful = true)
        }
    }
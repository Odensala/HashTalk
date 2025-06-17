package com.odensala.hashtalk.auth.domain.usecase

import javax.inject.Inject

class ValidateRepeatPasswordUseCase
    @Inject
    constructor() {
        operator fun invoke(
            password: String,
            repeatPassword: String,
        ): ValidationResult {
            if (repeatPassword != password) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "Passwords do not match",
                )
            }

            return ValidationResult(successful = true)
        }
    }
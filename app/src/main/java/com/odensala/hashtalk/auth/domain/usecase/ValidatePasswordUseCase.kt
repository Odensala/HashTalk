package com.odensala.hashtalk.auth.domain.usecase

import com.odensala.hashtalk.auth.domain.error.PasswordError
import com.odensala.hashtalk.core.domain.error.Result
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): Result<Unit, PasswordError> {
        if (password.length < 8) {
            return Result.Error(
                PasswordError.TOO_SHORT
            )
        }

        val containsLettersAndDigits =
            password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return Result.Error(
                PasswordError.INVALID
            )
        }

        return Result.Success(Unit)
    }
}
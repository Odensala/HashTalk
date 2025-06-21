package com.odensala.hashtalk.auth.domain.usecase

import com.odensala.hashtalk.auth.domain.error.PasswordError
import com.odensala.hashtalk.core.domain.error.Result
import javax.inject.Inject

class ValidateRepeatPasswordUseCase @Inject constructor() {
    operator fun invoke(password: String, repeatPassword: String): Result<Unit, PasswordError> {
        if (repeatPassword != password) {
            return Result.Error(
                PasswordError.NOT_MATCHING
            )
        }

        return Result.Success(Unit)
    }
}
package com.odensala.hashtalk.auth.domain.usecase

import com.odensala.hashtalk.auth.domain.error.EmailError
import com.odensala.hashtalk.core.domain.error.Result
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String): Result<Unit, EmailError> {
        if (email.isBlank()) {
            return Result.Error(EmailError.EMPTY)
        }

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (!email.matches(emailPattern.toRegex())) {
            return Result.Error(EmailError.INVALID)
        }

        return Result.Success(Unit)
    }
}
package com.odensala.hashtalk.auth.presentation.screen.signup

import com.odensala.hashtalk.auth.domain.usecase.ValidateEmailUseCase
import com.odensala.hashtalk.auth.domain.usecase.ValidatePasswordUseCase
import com.odensala.hashtalk.auth.domain.usecase.ValidateRepeatPasswordUseCase
import com.odensala.hashtalk.auth.presentation.screen.signup.error.EmailFieldError
import com.odensala.hashtalk.auth.presentation.screen.signup.error.PasswordFieldError
import com.odensala.hashtalk.auth.presentation.screen.signup.error.mapEmailErrorToUi
import com.odensala.hashtalk.auth.presentation.screen.signup.error.mapPasswordErrorToUi
import com.odensala.hashtalk.core.domain.error.Result
import javax.inject.Inject

class SignUpValidator @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatPasswordUseCase: ValidateRepeatPasswordUseCase
) {
    fun validateEmail(email: String): EmailFieldError? {
        return when (val result = validateEmailUseCase(email)) {
            is Result.Success -> null
            is Result.Error -> mapEmailErrorToUi(result.error)
        }
    }

    fun validatePassword(password: String): PasswordFieldError? {
        return when (val result = validatePasswordUseCase(password)) {
            is Result.Success -> null
            is Result.Error -> mapPasswordErrorToUi(result.error)
        }
    }

    fun validateRepeatPassword(password: String, repeatPassword: String): PasswordFieldError? {
        return when (val result = validateRepeatPasswordUseCase(password, repeatPassword)) {
            is Result.Success -> null
            is Result.Error -> mapPasswordErrorToUi(result.error)
        }
    }
}
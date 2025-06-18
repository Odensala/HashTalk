package com.odensala.hashtalk.auth.presentation.screen.signup

import com.odensala.hashtalk.auth.presentation.screen.signup.error.EmailFieldError
import com.odensala.hashtalk.auth.presentation.screen.signup.error.GeneralSignUpError
import com.odensala.hashtalk.auth.presentation.screen.signup.error.PasswordFieldError

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val emailError: EmailFieldError? = null,
    val passwordError: PasswordFieldError? = null,
    val repeatPasswordError: PasswordFieldError? = null,
    val isLoading: Boolean = false,
    val generalError: GeneralSignUpError? = null,
)
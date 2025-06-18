package com.odensala.hashtalk.auth.presentation.screen.signup.error

sealed class PasswordFieldError {
    object Empty : PasswordFieldError()

    object TooShort : PasswordFieldError()

    object Invalid : PasswordFieldError()

    object Weak : PasswordFieldError()

    object NotMatching : PasswordFieldError()

    object Unknown : PasswordFieldError()
}
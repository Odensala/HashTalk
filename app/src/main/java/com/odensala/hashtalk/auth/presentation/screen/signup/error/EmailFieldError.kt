package com.odensala.hashtalk.auth.presentation.screen.signup.error

sealed class EmailFieldError {
    object Empty : EmailFieldError()

    object Invalid : EmailFieldError()

    object AlreadyInUse : EmailFieldError()

    object Unknown : EmailFieldError()
}
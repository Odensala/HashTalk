package com.odensala.hashtalk.auth.presentation.screen.login.error

import com.odensala.hashtalk.core.domain.error.DataError

fun mapLoginErrorToUi(error: DataError.AuthError): AuthUiError {
    return when (error) {
        DataError.AuthError.WRONG_PASSWORD -> AuthUiError.InvalidCredentials
        DataError.AuthError.USER_NOT_FOUND -> AuthUiError.UserNotFound
        DataError.AuthError.EMAIL_ALREADY_IN_USE -> AuthUiError.EmailAlreadyInUse
        DataError.AuthError.WEAK_PASSWORD -> AuthUiError.WeakPassword
        DataError.AuthError.INVALID_EMAIL -> AuthUiError.InvalidCredentials
        DataError.AuthError.UNKNOWN -> AuthUiError.Unknown
    }
}
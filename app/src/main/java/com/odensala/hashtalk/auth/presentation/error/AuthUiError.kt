package com.odensala.hashtalk.auth.presentation.error

sealed class AuthUiError {
    object InvalidCredentials : AuthUiError()
    object UserNotFound : AuthUiError()
    object EmailAlreadyInUse : AuthUiError()
    object WeakPassword : AuthUiError()
    object FieldEmpty : AuthUiError()
    object Unknown : AuthUiError()
}
package com.odensala.hashtalk.auth.domain.model

sealed class AuthState {
    data object CheckingAuthentication : AuthState()

    data object Authenticated : AuthState()

    data object Unauthenticated : AuthState()
}
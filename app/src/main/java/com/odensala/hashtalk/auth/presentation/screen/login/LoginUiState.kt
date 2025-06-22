package com.odensala.hashtalk.auth.presentation.screen.login

import com.odensala.hashtalk.auth.presentation.screen.login.error.AuthUiError

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: AuthUiError? = null
)
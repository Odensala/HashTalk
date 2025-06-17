package com.odensala.hashtalk.auth.presentation.screen.signup

data class SignUpUiState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val repeatPassword: String = "",
    val repeatPasswordError: String? = null,
    val isLoading: Boolean = false,
    val error: String = "",
)
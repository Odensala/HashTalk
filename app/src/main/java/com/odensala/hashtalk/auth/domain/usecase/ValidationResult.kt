package com.odensala.hashtalk.auth.domain.usecase

data class ValidationResult(
    val successful: Boolean = false,
    val errorMessage: String? = null,
)
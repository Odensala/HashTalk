package com.odensala.hashtalk.core.domain.error

sealed interface DataError : Failure {
    enum class Auth : DataError {
        EMAIL_ALREADY_IN_USE,
        WEAK_PASSWORD,
        INVALID_EMAIL,
        UNKNOWN,
    }

    enum class PostError : DataError {
        UNAVAILABLE,
        PERMISSION_DENIED,
        UNKNOWN,
    }
}
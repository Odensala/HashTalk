package com.odensala.hashtalk.core.domain.error

sealed interface DataError : Failure {
    enum class AuthError : DataError {
        EMAIL_ALREADY_IN_USE,
        WEAK_PASSWORD,
        INVALID_EMAIL,
        WRONG_PASSWORD,
        USER_NOT_FOUND,
        UNKNOWN
    }

    enum class AuthStateError : DataError {
        NOT_FOUND,
        UNAUTHORIZED,
        UNKNOWN
    }

    enum class PostError : DataError {
        UNAVAILABLE,
        PERMISSION_DENIED,
        UNKNOWN
    }

    enum class ImageError : DataError {
        UPLOAD_FAILED
    }
}
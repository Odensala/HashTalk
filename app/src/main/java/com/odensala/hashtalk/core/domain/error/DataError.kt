package com.odensala.hashtalk.core.domain.error

/**
 * Domain-specific error types for the application.
 *
 * Sealed interface hierarchy that provides type-safe error handling across different
 * feature domains (Auth, Posts, Images). All errors extend the common RootError base
 * to ensure consistent error propagation through the Result wrapper type.
 */
sealed interface DataError : Failure {
    enum class AuthError : DataError {
        EMAIL_ALREADY_IN_USE,
        WEAK_PASSWORD,
        INVALID_EMAIL,
        WRONG_PASSWORD,
        USER_NOT_FOUND,
        UNKNOWN
    }

    // TODO: Map these errors to UI messages
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
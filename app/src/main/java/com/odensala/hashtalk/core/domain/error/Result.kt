package com.odensala.hashtalk.core.domain.error

typealias RootError = Failure

/**
 * Generic result wrapper that enforces type-safe error handling.
 *
 * Allows easy declaration of domain-specific error types through the E parameter
 * while maintaining compile-time safety and exhaustive error handling.
 */
sealed interface Result<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Result<D, E>

    data class Error<out D, out E : RootError>(val error: E) : Result<D, E>
}
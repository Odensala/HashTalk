package com.odensala.hashtalk.auth.domain.error

import com.odensala.hashtalk.core.domain.error.Failure

sealed interface DataError : Failure {
    enum class Auth : DataError {
        EMAIL_ALREADY_IN_USE,
        WEAK_PASSWORD,
        INVALID_EMAIL,
        UNKNOWN,
    }
}
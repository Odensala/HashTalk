package com.odensala.hashtalk.auth.domain.error

import com.odensala.hashtalk.core.domain.error.Failure

enum class PasswordError : Failure {
    EMPTY,
    WEAK,
    INVALID,
    NOT_MATCHING,
    TOO_SHORT
}
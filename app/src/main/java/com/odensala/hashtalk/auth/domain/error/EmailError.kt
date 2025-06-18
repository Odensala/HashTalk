package com.odensala.hashtalk.auth.domain.error

import com.odensala.hashtalk.core.domain.error.Failure

enum class EmailError : Failure {
    EMPTY,
    INVALID,
    ALREADY_IN_USE,
    UNKNOWN,
}
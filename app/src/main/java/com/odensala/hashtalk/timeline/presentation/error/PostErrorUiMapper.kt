package com.odensala.hashtalk.timeline.presentation.error

import com.odensala.hashtalk.core.domain.error.DataError

fun mapPostErrorToUi(error: DataError.PostError): PostUiError {
    return when (error) {
        DataError.PostError.PERMISSION_DENIED -> PostUiError.Unauthorized
        DataError.PostError.UNAVAILABLE -> PostUiError.Unavailable
        DataError.PostError.UNKNOWN -> PostUiError.Unknown
    }
}
package com.odensala.hashtalk.timeline.presentation.error

sealed class PostUiError {
    object Unauthorized : PostUiError()

    object Unavailable : PostUiError()

    object Unknown : PostUiError()
}
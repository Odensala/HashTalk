package com.odensala.hashtalk.timeline.presentation.error

sealed class ImageUiError {
    object UploadFailed : ImageUiError()
}
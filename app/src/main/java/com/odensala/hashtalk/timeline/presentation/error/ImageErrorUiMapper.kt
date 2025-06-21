package com.odensala.hashtalk.timeline.presentation.error

import com.odensala.hashtalk.core.domain.error.DataError

fun mapImageErrorToUiMessage(error: DataError.ImageError): ImageUiError {
    return when (error) {
        DataError.ImageError.UPLOAD_FAILED -> ImageUiError.UploadFailed
    }
}
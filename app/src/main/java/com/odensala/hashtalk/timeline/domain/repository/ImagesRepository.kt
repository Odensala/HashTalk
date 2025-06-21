package com.odensala.hashtalk.timeline.domain.repository

import android.net.Uri
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result

interface ImagesRepository {
    suspend fun uploadImage(uri: Uri): Result<String, DataError.ImageError>
}
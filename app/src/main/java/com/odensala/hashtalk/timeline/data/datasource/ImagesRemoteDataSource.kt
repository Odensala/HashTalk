package com.odensala.hashtalk.timeline.data.datasource

import android.net.Uri
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result

interface ImagesRemoteDataSource {
    suspend fun uploadImage(uri: Uri): Result<String, DataError.ImageError>
}
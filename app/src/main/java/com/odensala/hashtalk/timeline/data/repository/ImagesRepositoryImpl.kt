package com.odensala.hashtalk.timeline.data.repository

import android.net.Uri
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.timeline.data.datasource.ImagesRemoteDataSource
import com.odensala.hashtalk.timeline.domain.repository.ImagesRepository
import javax.inject.Inject

class ImagesRepositoryImpl
    @Inject
    constructor(
        private val imagesRemoteDataSource: ImagesRemoteDataSource,
    ) : ImagesRepository {
        override suspend fun uploadImage(uri: Uri): Result<String, DataError.ImageError> {
            return imagesRemoteDataSource.uploadImage(uri)
        }
    }
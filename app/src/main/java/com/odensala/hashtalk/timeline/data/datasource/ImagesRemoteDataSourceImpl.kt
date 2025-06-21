package com.odensala.hashtalk.timeline.data.datasource

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

private const val TAG = "ImagesRemoteDataSource"

class ImagesRemoteDataSourceImpl @Inject constructor(
    private val storage: FirebaseStorage
) : ImagesRemoteDataSource {

    override suspend fun uploadImage(uri: Uri): Result<String, DataError.ImageError> {
        return try {
            val uuid = UUID.randomUUID().toString()
            val fileName = "post_images/$uuid"

            val ref = storage.reference.child(fileName)
            ref.putFile(uri).await()

            val downloadUrl = ref.downloadUrl.await().toString()
            Result.Success(downloadUrl)
        } catch (e: Exception) {
            Log.e(TAG, "Error uploading image", e)
            Result.Error(DataError.ImageError.UPLOAD_FAILED)
        }
    }
}
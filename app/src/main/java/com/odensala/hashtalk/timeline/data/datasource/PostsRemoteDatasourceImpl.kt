package com.odensala.hashtalk.timeline.data.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.timeline.data.model.Post
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "PostsRemoteDatasource"

class PostsRemoteDatasourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : PostsRemoteDataSource {

    private val postsCollection = firestore.collection("posts")

    override suspend fun addPost(post: Post): Result<Unit, DataError.PostError> {
        return try {
            postsCollection.add(post).await()
            Result.Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            val error = when (e.code) {
                FirebaseFirestoreException.Code.UNAVAILABLE -> DataError.PostError.UNAVAILABLE
                else -> DataError.PostError.UNKNOWN
            }
            Result.Error(error)
        } catch (e: Exception) {
            Log.e(TAG, "Error adding post", e)
            Result.Error(DataError.PostError.UNKNOWN)
        }
    }

    override fun getPostsFlow(): Flow<Result<List<Post>, DataError.PostError>> = callbackFlow {
        Log.d(TAG, "Starting posts flow...")
        val listener = postsCollection.orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    val postError = when (error.code) {
                        FirebaseFirestoreException.Code.UNAVAILABLE -> DataError.PostError.UNAVAILABLE
                        FirebaseFirestoreException.Code.PERMISSION_DENIED -> DataError.PostError.PERMISSION_DENIED

                        else -> DataError.PostError.UNKNOWN
                    }
                    trySend(Result.Error(postError))

                    return@addSnapshotListener
                }

                val posts = snapshot?.documents?.mapNotNull { doc ->
                    try {
                        doc.toObject(Post::class.java)?.copy(id = doc.id)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error converting post", e)
                        null
                    }
                } ?: emptyList()

                trySend(Result.Success(posts))
            }

        awaitClose { listener.remove() }
    }
}
package com.odensala.hashtalk.timeline.data.datasource

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

class PostsRemoteDatasourceImpl
    @Inject
    constructor(
        private val firestore: FirebaseFirestore,
    ) : PostsRemoteDataSource {
        private val postsCollection = firestore.collection("posts")

        override suspend fun addPost(post: Post): Result<Unit, DataError.PostError> {
            return try {
                postsCollection.add(post).await()
                Result.Success(Unit)
            } catch (e: FirebaseFirestoreException) {
                val error =
                    when (e.code) {
                        FirebaseFirestoreException.Code.UNAVAILABLE -> DataError.PostError.UNAVAILABLE
                        else -> DataError.PostError.UNKNOWN
                    }
                Result.Error(error)
            } catch (e: Exception) {
                Result.Error(DataError.PostError.UNKNOWN)
            }
        }

        override fun getPostsFlow(): Flow<Result<List<Post>, DataError.PostError>> =
            callbackFlow {
                val listener =
                    postsCollection
                        .orderBy("timestamp", Query.Direction.DESCENDING)
                        .addSnapshotListener { snapshot, error ->
                            if (error != null) {
                                val postError =
                                    when (error.code) {
                                        FirebaseFirestoreException.Code.UNAVAILABLE -> DataError.PostError.UNAVAILABLE
                                        FirebaseFirestoreException.Code.PERMISSION_DENIED -> DataError.PostError.PERMISSION_DENIED
                                        else -> DataError.PostError.UNKNOWN
                                    }
                                trySend(Result.Error(postError))
                                close(error)
                                return@addSnapshotListener
                            }

                            val posts =
                                snapshot?.documents?.mapNotNull { doc ->
                                    doc.toObject(Post::class.java)?.copy(id = doc.id)
                                } ?: emptyList()

                            trySend(Result.Success(posts))
                        }

                awaitClose { listener.remove() }
            }
    }
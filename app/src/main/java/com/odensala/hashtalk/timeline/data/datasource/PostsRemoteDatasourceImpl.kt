package com.odensala.hashtalk.timeline.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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

        override suspend fun addPost(post: Post) {
            postsCollection.add(post).await()
        }

        override fun getPostsFlow(): Flow<List<Post>> =
            callbackFlow {
                val listener =
                    postsCollection
                        .orderBy("timestamp", Query.Direction.DESCENDING)
                        .addSnapshotListener { snapshot, error ->
                            if (error != null) {
                                close(error)
                                return@addSnapshotListener
                            }

                            val posts =
                                snapshot?.documents?.mapNotNull { doc ->
                                    doc.toObject(Post::class.java)?.copy(id = doc.id)
                                } ?: emptyList()

                            trySend(posts)
                        }

                awaitClose { listener.remove() }
            }
    }
package com.odensala.hashtalk.timeline.data.repository

import com.odensala.hashtalk.auth.data.datasource.FirebaseAuthDataSource
import com.odensala.hashtalk.timeline.data.datasource.PostsRemoteDataSource
import com.odensala.hashtalk.timeline.data.model.Post
import com.odensala.hashtalk.timeline.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class PostsRepositoryImpl
    @Inject
    constructor(
        private val fireStore: PostsRemoteDataSource,
        private val firebaseAuth: FirebaseAuthDataSource,
    ) : PostsRepository {
        override val posts: Flow<List<Post>> =
            fireStore.getPostsFlow()
                .distinctUntilChanged()

        override suspend fun addPost(post: String) {
            val currentUser = firebaseAuth.getCurrentUser()
            if (currentUser != null) {
                val post =
                    Post(
                        userId = currentUser.uid,
                        userEmail = currentUser.email,
                        content = post,
                    )

                fireStore.addPost(post)
            } else {
            }
        }
    }
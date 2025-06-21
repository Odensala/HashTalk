package com.odensala.hashtalk.timeline.data.repository

import com.odensala.hashtalk.auth.data.datasource.AuthRemoteDataSource
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.timeline.data.datasource.PostsRemoteDataSource
import com.odensala.hashtalk.timeline.data.model.Post
import com.odensala.hashtalk.timeline.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class PostsRepositoryImpl
    @Inject
    constructor(
        private val postsRemoteDataSource: PostsRemoteDataSource,
        private val authRemoteDataSource: AuthRemoteDataSource,
    ) : PostsRepository {
        override val posts: Flow<Result<List<Post>, DataError.PostError>> =
            postsRemoteDataSource.getPostsFlow()
                .distinctUntilChanged()

        override suspend fun addPost(
            postContent: String,
            imageUrl: String?,
        ): Result<Unit, DataError.PostError> {
            val currentUser = authRemoteDataSource.getCurrentUser()
            if (currentUser != null) {
                val post =
                    Post(
                        userId = currentUser.uid,
                        imageUrl = imageUrl,
                        userEmail = currentUser.email,
                        content = postContent,
                    )

                return postsRemoteDataSource.addPost(post)
            } else {
                return Result.Error(DataError.PostError.PERMISSION_DENIED)
            }
        }
    }
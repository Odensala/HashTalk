package com.odensala.hashtalk.timeline.data.datasource

import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.timeline.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostsRemoteDataSource {
    suspend fun addPost(post: Post): Result<Unit, DataError.PostError>

    fun getPostsFlow(): Flow<Result<List<Post>, DataError.PostError>>
}
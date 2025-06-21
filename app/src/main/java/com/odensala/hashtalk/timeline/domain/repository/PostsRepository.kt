package com.odensala.hashtalk.timeline.domain.repository

import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.timeline.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    val posts: Flow<Result<List<Post>, DataError.PostError>>

    suspend fun addPost(post: String, imageUrl: String? = null): Result<Unit, DataError.PostError>
}
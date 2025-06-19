package com.odensala.hashtalk.timeline.data.datasource

import com.odensala.hashtalk.timeline.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostsRemoteDataSource {
    suspend fun addPost(post: Post)

    fun getPostsFlow(): Flow<List<Post>>
}
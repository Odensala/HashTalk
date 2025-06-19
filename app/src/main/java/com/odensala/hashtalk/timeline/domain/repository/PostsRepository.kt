package com.odensala.hashtalk.timeline.domain.repository

import com.odensala.hashtalk.timeline.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    val posts: Flow<List<Post>>

    suspend fun addPost(post: String)
}
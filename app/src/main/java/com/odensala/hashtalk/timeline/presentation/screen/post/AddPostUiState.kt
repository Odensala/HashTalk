package com.odensala.hashtalk.timeline.presentation.screen.post

import com.odensala.hashtalk.timeline.presentation.error.PostUiError

data class AddPostUiState(
    val postContent: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: PostUiError? = null,
)
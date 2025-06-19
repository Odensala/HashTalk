package com.odensala.hashtalk.timeline.presentation.screen.post

data class AddPostUiState(
    val postContent: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
)
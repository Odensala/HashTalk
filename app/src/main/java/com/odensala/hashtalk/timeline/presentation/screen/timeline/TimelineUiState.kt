package com.odensala.hashtalk.timeline.presentation.screen.timeline

import com.odensala.hashtalk.timeline.presentation.error.PostUiError

data class TimelineUiState(
    val posts: List<PostUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val isLoggingOut: Boolean = false,
    val error: PostUiError? = null,
)
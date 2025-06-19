package com.odensala.hashtalk.timeline.presentation.screen.timeline

data class TimelineUiState(
    val posts: List<PostUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val isLoggingOut: Boolean = false,
    val error: String? = null,
)
package com.odensala.hashtalk.timeline.presentation.screen.timeline

import com.google.firebase.Timestamp

data class PostUiModel(
    val id: String = "",
    val userEmail: String = "",
    val content: String = "",
    val imageUrl: String? = null,
    val timestamp: Timestamp,
)
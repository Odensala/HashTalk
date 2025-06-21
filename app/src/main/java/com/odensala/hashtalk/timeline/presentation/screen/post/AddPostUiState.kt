package com.odensala.hashtalk.timeline.presentation.screen.post

import android.net.Uri
import com.odensala.hashtalk.timeline.presentation.error.ImageUiError
import com.odensala.hashtalk.timeline.presentation.error.PostUiError

data class AddPostUiState(
    val postContent: String = "",
    val selectedImageUri: Uri? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val postError: PostUiError? = null,
    val imageError: ImageUiError? = null,
)
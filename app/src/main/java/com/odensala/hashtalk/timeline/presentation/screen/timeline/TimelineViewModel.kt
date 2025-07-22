package com.odensala.hashtalk.timeline.presentation.screen.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odensala.hashtalk.auth.domain.repository.AuthRepository
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.timeline.data.model.Post
import com.odensala.hashtalk.timeline.domain.repository.PostsRepository
import com.odensala.hashtalk.timeline.presentation.error.mapPostErrorToUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val deleteSuccess: MutableStateFlow(false)

    val uiState: StateFlow<TimelineUiState> =
        postsRepository.posts
            .map { result ->
                when (result) {
                    is Result.Success ->
                        TimelineUiState(
                            posts = result.data.map { post ->
                                post.toUiModel()
                            },
                            isLoading = false,
                            error = null
                        )

                    is Result.Error ->
                        TimelineUiState(
                            isLoading = false,
                            error = mapPostErrorToUi(result.error)
                        )
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = TimelineUiState(isLoading = true)
            )

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun deletePost(postId: String) {
        viewModelScope.launch {
             val result = postsRepository.deletePost(postId)
        }
    }

    private fun Post.toUiModel() = PostUiModel(
        id = id,
        userEmail = userEmail,
        content = content,
        imageUrl = imageUrl,
        timestamp = timestamp
    )
}
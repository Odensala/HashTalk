package com.odensala.hashtalk.timeline.presentation.screen.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.timeline.domain.repository.PostsRepository
import com.odensala.hashtalk.timeline.presentation.error.mapPostErrorToUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel
    @Inject
    constructor(private val postsRepository: PostsRepository) : ViewModel() {
        val uiState: StateFlow<TimelineUiState> =
            postsRepository.posts
                .map { result ->
                    when (result) {
                        is Result.Success ->
                            TimelineUiState(
                                posts =
                                    result.data.map { post ->
                                        PostUiModel(
                                            id = post.id,
                                            userEmail = post.userEmail,
                                            content = post.content,
                                            imageUrl = post.imageUrl,
                                            timestamp = post.timestamp,
                                        )
                                    },
                                isLoading = false,
                                error = null,
                            )
                        is Result.Error ->
                            TimelineUiState(
                                isLoading = false,
                                error = mapPostErrorToUi(result.error),
                            )
                    }
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = TimelineUiState(isLoading = true),
                )

        fun logout() {
        }
    }
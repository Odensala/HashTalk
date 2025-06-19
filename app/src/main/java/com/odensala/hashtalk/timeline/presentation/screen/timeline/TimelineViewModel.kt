package com.odensala.hashtalk.timeline.presentation.screen.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odensala.hashtalk.timeline.domain.repository.PostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel
    @Inject
    constructor(private val postsRepository: PostsRepository) : ViewModel() {
        val uiState: StateFlow<TimelineUiState> =
            postsRepository.posts
                .map { posts ->
                    TimelineUiState(
                        posts =
                            posts.map { post ->
                                PostUiModel(
                                    id = post.id,
                                    content = post.content,
                                    imageUrl = post.imageUrl,
                                    timestamp = post.timestamp,
                                )
                            },
                        isLoading = false,
                        error = null,
                    )
                }
                .catch { error ->
                    emit(TimelineUiState(isLoading = false, error = error.message))
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = TimelineUiState(isLoading = true),
                )

        fun logout() {
        }
    }
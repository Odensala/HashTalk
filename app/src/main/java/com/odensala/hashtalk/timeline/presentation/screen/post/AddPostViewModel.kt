package com.odensala.hashtalk.timeline.presentation.screen.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.timeline.domain.repository.PostsRepository
import com.odensala.hashtalk.timeline.presentation.error.mapPostErrorToUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel
    @Inject
    constructor(
        private val postsRepository: PostsRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(AddPostUiState())
        val uiState: StateFlow<AddPostUiState> = _uiState.asStateFlow()

        fun addPost(content: String) {
            viewModelScope.launch {
                _uiState.update { state ->
                    state.copy(isLoading = true, error = null)
                }

                val result = postsRepository.addPost(content.trim())
                _uiState.update { state ->
                    when (result) {
                        is Result.Success ->
                            state.copy(
                                isLoading = false,
                                isSuccess = true,
                                error = null,
                            )

                        is Result.Error ->
                            state.copy(
                                isLoading = false,
                                isSuccess = false,
                                error = mapPostErrorToUi(result.error),
                            )
                    }
                }
            }
        }

        fun onPostContentChange(newContent: String) {
            _uiState.update { state ->
                state.copy(postContent = newContent)
            }
        }

        fun clearError() {
            _uiState.update { state ->
                state.copy(error = null)
            }
        }

        fun resetSuccess() {
            _uiState.update { state ->
                state.copy(isSuccess = false)
            }
        }
    }
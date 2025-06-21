package com.odensala.hashtalk.timeline.presentation.screen.post

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.timeline.domain.repository.ImagesRepository
import com.odensala.hashtalk.timeline.domain.repository.PostsRepository
import com.odensala.hashtalk.timeline.presentation.error.mapImageErrorToUiMessage
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
        private val imagesRepository: ImagesRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(AddPostUiState())
        val uiState: StateFlow<AddPostUiState> = _uiState.asStateFlow()

        fun addPost(content: String) {
            viewModelScope.launch {
                _uiState.update { state ->
                    state.copy(isLoading = true, postError = null)
                }

                val imageUri = uiState.value.selectedImageUri
                var imageUrl: String? = null

                if (imageUri != null) {
                    val uploadResult = imagesRepository.uploadImage(imageUri)
                    when (uploadResult) {
                        is Result.Success -> imageUrl = uploadResult.data
                        is Result.Error -> {
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    imageError = mapImageErrorToUiMessage(uploadResult.error),
                                )
                            }
                            return@launch
                        }
                    }
                }

                val result = postsRepository.addPost(content.trim(), imageUrl)
                _uiState.update { state ->
                    when (result) {
                        is Result.Success ->
                            state.copy(
                                isLoading = false,
                                isSuccess = true,
                                postError = null,
                            )

                        is Result.Error ->
                            state.copy(
                                isLoading = false,
                                isSuccess = false,
                                postError = mapPostErrorToUi(result.error),
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
                state.copy(
                    postError = null,
                    imageError = null,
                )
            }
        }

        fun resetSuccess() {
            _uiState.update { state ->
                state.copy(isSuccess = false)
            }
        }

        fun onImageSelected(uri: Uri?) {
            _uiState.update { state ->
                state.copy(selectedImageUri = uri)
            }
        }
    }
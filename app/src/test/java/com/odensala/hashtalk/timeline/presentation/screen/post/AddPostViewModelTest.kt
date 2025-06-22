package com.odensala.hashtalk.timeline.presentation.screen.post

import android.net.Uri
import com.google.common.truth.Truth.assertThat
import com.odensala.hashtalk.MainCoroutineTestRule
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.timeline.domain.repository.ImagesRepository
import com.odensala.hashtalk.timeline.domain.repository.PostsRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddPostViewModelTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var postsRepository: PostsRepository

    @MockK
    private lateinit var imagesRepository: ImagesRepository

    private lateinit var viewModel: AddPostViewModel

    @Before
    fun setUp() {
        viewModel = AddPostViewModel(postsRepository, imagesRepository)
    }

    @Test
    fun `addPost without image, posts successfully`() = runTest {
        coEvery { postsRepository.addPost("content", null) } returns Result.Success(Unit)

        viewModel.addPost("content")
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.isSuccess).isTrue()
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun `addPost with image, uploads image then posts successfully`() = runTest {
        val mockUri = mockk<Uri>()

        coEvery { imagesRepository.uploadImage(mockUri) } returns Result.Success("image-url")
        coEvery { postsRepository.addPost("content", "image-url") } returns Result.Success(Unit)

        viewModel.onImageSelected(mockUri)
        viewModel.addPost("content")
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.isSuccess).isTrue()
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun `addPost failure, sets error state`() = runTest {
        coEvery { postsRepository.addPost(any(), any()) } returns Result.Error(DataError.PostError.UNAVAILABLE)

        viewModel.addPost("content")
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.postError).isNotNull()
        assertThat(viewModel.uiState.value.isSuccess).isFalse()
    }

    @Test
    fun `onPostContentChange updates content in state`() {
        viewModel.onPostContentChange("new content")

        assertThat(viewModel.uiState.value.postContent).isEqualTo("new content")
    }
}
package com.odensala.hashtalk.timeline.presentation.screen.timeline

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.odensala.hashtalk.MainCoroutineTestRule
import com.odensala.hashtalk.auth.domain.repository.AuthRepository
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.timeline.data.model.Post
import com.odensala.hashtalk.timeline.domain.repository.PostsRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TimelineViewModelTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var postsRepository: PostsRepository

    @MockK
    private lateinit var authRepository: AuthRepository

    private lateinit var viewModel: TimelineViewModel
    private lateinit var postsFlow: MutableSharedFlow<Result<List<Post>, DataError.PostError>>

    @Before
    fun setUp() {
        postsFlow = MutableSharedFlow()
        every { postsRepository.posts } returns postsFlow
        viewModel = TimelineViewModel(postsRepository, authRepository)
    }

    @Test
    fun `when posts load successfully, then ui state shows posts`() = runTest {
        val mockPosts = listOf(
            Post(
                id = "1",
                userId = "doraemon",
                userEmail = "doraemon@gmail.com",
                content = "Hello Nobita"
            ),
            Post(
                id = "2",
                userId = "nobita",
                userEmail = "nobita@gmail.com",
                content = "Hello Doraemon"
            )
        )

        viewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.isLoading).isTrue()

            postsFlow.emit(Result.Success(mockPosts))

            // UI state should show the posts
            val successState = awaitItem()
            assertThat(successState.isLoading).isFalse()
            assertThat(successState.error).isNull()
            assertThat(successState.posts).hasSize(2)
            assertThat(successState.posts[0].content).isEqualTo("Hello Nobita")
            assertThat(successState.posts[1].content).isEqualTo("Hello Doraemon")
        }
    }

    @Test
    fun `when posts fail to load, then ui state shows error`() = runTest {
        // Currently network errors are mapped to UNKNOWN
        val error = DataError.PostError.UNKNOWN

        viewModel.uiState.test {
            awaitItem()

            postsFlow.emit(Result.Error(error))

            val errorState = awaitItem()
            assertThat(errorState.isLoading).isFalse()
            assertThat(errorState.posts).isEmpty()
            assertThat(errorState.error).isNotNull()
        }
    }
}
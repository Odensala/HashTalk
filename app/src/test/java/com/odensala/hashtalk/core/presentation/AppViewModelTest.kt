
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.odensala.hashtalk.MainCoroutineTestRule
import com.odensala.hashtalk.auth.domain.model.AuthState
import com.odensala.hashtalk.auth.domain.repository.AuthRepository
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.core.presentation.AppViewModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModelTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var authRepository: AuthRepository

    private lateinit var viewModel: AppViewModel
    private val authStateFlow = MutableStateFlow<Result<AuthState, DataError.AuthStateError>>(
        Result.Success(AuthState.CheckingAuthentication)
    )

    @Before
    fun setUp() {
        every { authRepository.authState } returns authStateFlow
        viewModel = AppViewModel(authRepository)
    }

    @Test
    fun `initial state is CheckingAuthentication`() = runTest {
        assertThat(viewModel.authState.value).isEqualTo(AuthState.CheckingAuthentication)
    }

    @Test
    fun `success result maps to authenticated state`() = runTest {
        viewModel.authState.test {
            assertThat(awaitItem()).isEqualTo(AuthState.CheckingAuthentication)

            authStateFlow.value = Result.Success(AuthState.Authenticated)
            assertThat(awaitItem()).isEqualTo(AuthState.Authenticated)
        }
    }
}
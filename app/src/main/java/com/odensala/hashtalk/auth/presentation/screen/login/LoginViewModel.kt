package com.odensala.hashtalk.auth.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odensala.hashtalk.auth.domain.repository.AuthRepository
import com.odensala.hashtalk.auth.presentation.error.AuthUiError
import com.odensala.hashtalk.auth.presentation.error.mapLoginErrorToUi
import com.odensala.hashtalk.core.domain.error.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onLoginClick() {
        val currentState = _uiState.value
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _uiState.value = currentState.copy(error = AuthUiError.FieldEmpty)
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, error = null)

            val result =
                authRepository.login(
                    currentState.email,
                    currentState.password
                )

            when (result) {
                is Result.Success -> {
                    _uiState.value =
                        currentState.copy(
                            isLoading = false,
                            error = null
                        )
                }

                is Result.Error -> {
                    _uiState.value =
                        currentState.copy(
                            isLoading = false,
                            error = mapLoginErrorToUi(result.error)
                        )
                }
            }
        }
    }
}
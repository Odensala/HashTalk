package com.odensala.hashtalk.auth.presentation.screen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odensala.hashtalk.auth.domain.repository.AuthRepository
import com.odensala.hashtalk.auth.domain.usecase.ValidateEmailUseCase
import com.odensala.hashtalk.auth.domain.usecase.ValidatePasswordUseCase
import com.odensala.hashtalk.auth.domain.usecase.ValidateRepeatPasswordUseCase
import com.odensala.hashtalk.auth.domain.usecase.ValidationResult
import com.odensala.hashtalk.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
    @Inject
    constructor(
        private val validateEmailUseCase: ValidateEmailUseCase,
        private val validatePasswordUseCase: ValidatePasswordUseCase,
        private val validateRepeatPasswordUseCase: ValidateRepeatPasswordUseCase,
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SignUpUiState())
        val uiState = _uiState.asStateFlow()

        fun onEmailChange(email: String) {
            _uiState.update { state ->
                state.copy(
                    email = email,
                )
            }
        }

        fun onPasswordChange(password: String) {
            _uiState.update { state ->
                state.copy(
                    password = password,
                    passwordError = null,
                )
            }
        }

        fun onRepeatPasswordChange(repeatPassword: String) {
            _uiState.update { state ->
                state.copy(
                    repeatPassword = repeatPassword,
                    repeatPasswordError = null,
                )
            }
        }

        fun onSignUpClick() {
            val emailResult = validateEmail()
            val passwordResult = validatePassword()
            val repeatPasswordResult = validateRepeatPassword()

            val hasError =
                listOf(
                    emailResult,
                    passwordResult,
                    repeatPasswordResult,
                ).any { !it.successful }

            if (hasError) {
                return
            }

            // If all validations pass, proceed with sign up
            signUp()
        }

        private fun validateEmail(): ValidationResult {
            val result = validateEmailUseCase(_uiState.value.email)

            _uiState.update { state ->
                state.copy(
                    emailError = result.errorMessage,
                )
            }

            return result
        }

        private fun validatePassword(): ValidationResult {
            val result = validatePasswordUseCase(_uiState.value.password)

            _uiState.update { state ->
                state.copy(
                    passwordError = result.errorMessage,
                )
            }

            return result
        }

        private fun validateRepeatPassword(): ValidationResult {
            val result =
                validateRepeatPasswordUseCase(
                    _uiState.value.password,
                    _uiState.value.repeatPassword,
                )

            _uiState.update { state ->
                state.copy(
                    repeatPasswordError = result.errorMessage,
                )
            }

            return result
        }

        private fun signUp() {
            val currentState = _uiState.value

            viewModelScope.launch {
                _uiState.update { state ->
                    state.copy(
                        isLoading = true,
                        error = "",
                    )
                }

                val result =
                    authRepository.signUp(
                        currentState.email,
                        currentState.password,
                    )

                when (result) {
                    is Resource.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = "",
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = result.message ?: "Sign up failed",
                            )
                        }
                    }
                }
            }
        }
    }
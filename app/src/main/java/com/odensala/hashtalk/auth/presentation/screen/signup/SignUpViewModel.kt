package com.odensala.hashtalk.auth.presentation.screen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odensala.hashtalk.auth.domain.error.DataError
import com.odensala.hashtalk.auth.domain.repository.AuthRepository
import com.odensala.hashtalk.auth.presentation.screen.signup.error.EmailFieldError
import com.odensala.hashtalk.auth.presentation.screen.signup.error.GeneralSignUpError
import com.odensala.hashtalk.auth.presentation.screen.signup.error.PasswordFieldError
import com.odensala.hashtalk.core.domain.error.Result
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
    private val formValidator: SignUpValidator,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { state ->
            state.copy(
                email = email,
                emailError = null,
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { state ->
            state.copy(
                password = password,
                passwordError = null,
                repeatPasswordError = null,
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
        val currentState = _uiState.value

        // Validate all fields and show errors
        val emailError = formValidator.validateEmail(currentState.email)
        val passwordError = formValidator.validatePassword(currentState.password)
        val repeatPasswordError =
            formValidator.validateRepeatPassword(
                currentState.password,
                currentState.repeatPassword,
            )

        _uiState.update { state ->
            state.copy(
                emailError = emailError,
                passwordError = passwordError,
                repeatPasswordError = repeatPasswordError,
                generalError = null,
            )
        }

        if (emailError == null && passwordError == null && repeatPasswordError == null) {
            signUp()
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result =
                authRepository.signUp(
                    _uiState.value.email,
                    _uiState.value.password,
                )

            _uiState.update { it.copy(isLoading = false) }

            when (result) {
                is Result.Success -> {
                }

                is Result.Error -> handleSignUpError(result.error)
            }
        }
    }

    private fun handleSignUpError(error: DataError.Auth) {
        when (error) {
            DataError.Auth.EMAIL_ALREADY_IN_USE -> {
                _uiState.update { it.copy(emailError = EmailFieldError.AlreadyInUse) }
            }

            DataError.Auth.INVALID_EMAIL -> {
                _uiState.update { it.copy(emailError = EmailFieldError.Invalid) }
            }

            DataError.Auth.WEAK_PASSWORD -> {
                _uiState.update { it.copy(passwordError = PasswordFieldError.Weak) }
            }

            DataError.Auth.UNKNOWN -> {
                _uiState.update { it.copy(generalError = GeneralSignUpError.Unknown) }
            }
        }
    }
}
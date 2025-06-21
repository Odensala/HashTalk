package com.odensala.hashtalk.auth.presentation.screen.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.odensala.hashtalk.R
import com.odensala.hashtalk.auth.presentation.components.AuthTextField
import com.odensala.hashtalk.auth.presentation.screen.signup.error.EmailFieldError
import com.odensala.hashtalk.auth.presentation.screen.signup.error.GeneralSignUpError
import com.odensala.hashtalk.auth.presentation.screen.signup.error.PasswordFieldError
import com.odensala.hashtalk.core.presentation.components.ErrorMessage
import com.odensala.hashtalk.core.presentation.components.LoadingButton

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = hiltViewModel(), onNavigateBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()

    SignUpContent(
        uiState = state,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = viewModel::onSignUpClick,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpContent(
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.sign_up)) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription =
                            stringResource(
                                R.string.back
                            )
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(stringResource(R.string.sign_up), style = MaterialTheme.typography.headlineLarge)

            Spacer(Modifier.height(16.dp))

            // Email field
            AuthTextField(
                value = uiState.email,
                onValueChange = onEmailChange,
                label = stringResource(R.string.email),
                enabled = !uiState.isLoading
            )

            uiState.emailError?.let { error ->
                ErrorMessage(error = stringResource(emailFieldErrorMessageRes(error)))
            }

            Spacer(Modifier.height(8.dp))

            // Password field
            AuthTextField(
                value = uiState.password,
                onValueChange = onPasswordChange,
                label = stringResource(R.string.password),
                isPassword = true,
                enabled = !uiState.isLoading
            )

            uiState.passwordError?.let { error ->
                ErrorMessage(error = stringResource(passwordFieldErrorMessageRes(error)))
            }

            // Repeat password field
            AuthTextField(
                value = uiState.repeatPassword,
                onValueChange = onRepeatPasswordChange,
                label = stringResource(R.string.repeat_password),
                isPassword = true,
                enabled = !uiState.isLoading
            )

            uiState.repeatPasswordError?.let { error ->
                ErrorMessage(error = stringResource(passwordFieldErrorMessageRes(error)))
            }

            // General error message
            uiState.generalError?.let { error ->
                ErrorMessage(error = stringResource(mapGeneralSignUpErrorToUi(error)))
            }

            Spacer(Modifier.height(16.dp))

            LoadingButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onSignUpClick,
                text = stringResource(R.string.sign_up),
                isLoading = uiState.isLoading
            )
        }
    }
}

fun mapGeneralSignUpErrorToUi(error: GeneralSignUpError): Int = when (error) {
    GeneralSignUpError.Unknown -> R.string.unknown_error
    GeneralSignUpError.Network -> R.string.network_error
}

fun emailFieldErrorMessageRes(error: EmailFieldError): Int = when (error) {
    EmailFieldError.Empty -> R.string.email_empty
    EmailFieldError.Invalid -> R.string.email_invalid_format
    EmailFieldError.AlreadyInUse -> R.string.email_already_in_use
    EmailFieldError.Unknown -> R.string.unknown_error
}

fun passwordFieldErrorMessageRes(error: PasswordFieldError): Int = when (error) {
    PasswordFieldError.Empty -> R.string.password_empty
    PasswordFieldError.TooShort -> R.string.password_too_short
    PasswordFieldError.NotMatching -> R.string.passwords_do_not_match
    PasswordFieldError.Weak -> R.string.password_requires_one_letter_one_digit
    PasswordFieldError.Invalid -> R.string.password_invalid
    PasswordFieldError.Unknown -> R.string.unknown_error
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpContent(
        uiState =
        SignUpUiState(
            email = "doraemon@gmail.com",
            password = "password",
            isLoading = false
        ),
        onEmailChange = {},
        onPasswordChange = {},
        onSignUpClick = {},
        onRepeatPasswordChange = {},
        onNavigateBack = {}
    )
}

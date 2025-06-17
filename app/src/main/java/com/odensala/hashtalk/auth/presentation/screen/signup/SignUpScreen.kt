package com.odensala.hashtalk.auth.presentation.screen.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.odensala.hashtalk.core.presentation.components.ErrorMessage
import com.odensala.hashtalk.core.presentation.components.LoadingButton

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    SignUpContent(
        uiState = state,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = viewModel::onSignUpClick,
        onNavigateBack = onNavigateBack,
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
    onNavigateBack: () -> Unit,
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
                                    R.string.back,
                                ),
                        )
                    }
                },
            )
        },
    ) { paddingValues ->

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(stringResource(R.string.sign_up), style = MaterialTheme.typography.headlineLarge)

            Spacer(Modifier.height(16.dp))

            // Email field
            AuthTextField(
                value = uiState.email,
                onValueChange = onEmailChange,
                label = stringResource(R.string.email),
                enabled = !uiState.isLoading,
            )

            uiState.emailError?.let { error ->
                ErrorMessage(error = error)
            }

            Spacer(Modifier.height(8.dp))

            // Password field
            AuthTextField(
                value = uiState.password,
                onValueChange = onPasswordChange,
                label = stringResource(R.string.password),
                isPassword = true,
                enabled = !uiState.isLoading,
            )

            uiState.passwordError?.let { error ->
                ErrorMessage(error = error)
            }

            // Repeat password field
            AuthTextField(
                value = uiState.repeatPassword,
                onValueChange = onRepeatPasswordChange,
                label = stringResource(R.string.repeat_password),
                isPassword = true,
                enabled = !uiState.isLoading,
            )

            uiState.repeatPasswordError?.let { error ->
                ErrorMessage(error = error)
            }

            // General error message
            ErrorMessage(error = uiState.error)

            Spacer(Modifier.height(16.dp))

            LoadingButton(
                onClick = onSignUpClick,
                text = stringResource(R.string.sign_up),
                isLoading = uiState.isLoading,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpContent(
        uiState =
            SignUpUiState(
                email = "doraemon@gmail.com",
                password = "password",
                isLoading = false,
                error = "",
            ),
        onEmailChange = {},
        onPasswordChange = {},
        onSignUpClick = {},
        onRepeatPasswordChange = {},
        onNavigateBack = {},
    )
}

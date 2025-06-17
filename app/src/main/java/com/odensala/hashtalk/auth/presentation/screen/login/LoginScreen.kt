package com.odensala.hashtalk.auth.presentation.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToSignUp: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    LoginContent(
        uiState = state,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::onLoginClick,
        onNavigateToSignUp = onNavigateToSignUp,
    )
}

@Composable
fun LoginContent(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(stringResource(R.string.hash_talk), style = MaterialTheme.typography.headlineLarge)

        Spacer(Modifier.height(16.dp))

        // Email field
        AuthTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = stringResource(R.string.email),
            enabled = !uiState.isLoading,
        )

        Spacer(Modifier.height(8.dp))

        // Password field
        AuthTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            label = stringResource(R.string.password),
            isPassword = true,
            enabled = !uiState.isLoading,
        )

        ErrorMessage(error = uiState.error)

        Spacer(Modifier.height(16.dp))

        LoadingButton(
            onClick = onLoginClick,
            text = stringResource(R.string.login),
            isLoading = uiState.isLoading,
        )

        TextButton(onClick = onNavigateToSignUp) {
            Text(stringResource(R.string.sign_up))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginContent(
        uiState =
            LoginUiState(
                email = "doraemon@gmail.com",
                password = "password",
                isLoading = false,
                error = "",
            ),
        onEmailChange = {},
        onPasswordChange = {},
        onLoginClick = {},
        onNavigateToSignUp = {},
    )
}
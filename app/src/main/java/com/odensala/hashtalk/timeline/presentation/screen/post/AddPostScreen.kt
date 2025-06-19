package com.odensala.hashtalk.timeline.presentation.screen.post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.odensala.hashtalk.R
import com.odensala.hashtalk.core.presentation.components.LoadingButton
import com.odensala.hashtalk.timeline.domain.POST_MAX_CHAR
import com.odensala.hashtalk.timeline.presentation.components.AddPostTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostScreen(
    viewModel: AddPostViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            viewModel.resetSuccess()
            onNavigateBack()
        }
    }

    uiState.error?.let { error ->
        LaunchedEffect(error) {
            // TODO: Display snackbar
            viewModel.clearError()
        }
    }

    AddPostContent(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onPostContentChange = viewModel::onPostContentChange,
        onPostSubmit = viewModel::addPost,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostContent(
    uiState: AddPostUiState,
    onPostContentChange: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onPostSubmit: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    val isPostEnabled =
        remember(uiState.isLoading, uiState.postContent) {
            !uiState.isLoading && uiState.postContent.isNotBlank()
        }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .imePadding(),
    ) {
        TopAppBar(
            title = { Text(stringResource(R.string.add_post)) },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button_description),
                    )
                }
            },
            actions = {
                LoadingButton(
                    isLoading = uiState.isLoading,
                    enabled = isPostEnabled,
                    onClick = {
                        onPostSubmit(uiState.postContent)
                    },
                    modifier = Modifier.padding(end = 16.dp),
                    text = stringResource(R.string.post_button_text),
                )
            },
        )

        AddPostTextField(
            value = uiState.postContent,
            onValueChange = { onPostContentChange(it.take(POST_MAX_CHAR)) },
            focusRequester = focusRequester,
            enabled = !uiState.isLoading,
            modifier =
                Modifier
                    .weight(1f)
                    .padding(16.dp),
        )

        Text(
            text = "${uiState.postContent.length} / $POST_MAX_CHAR",
            modifier =
                Modifier
                    .align(Alignment.End)
                    .padding(16.dp),
        )
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddPostContent() {
    AddPostContent(
        uiState =
            AddPostUiState(
                isLoading = false,
                postContent = "",
                error = null,
                isSuccess = false,
            ),
        onNavigateBack = {},
        onPostSubmit = { },
        onPostContentChange = { },
    )
}
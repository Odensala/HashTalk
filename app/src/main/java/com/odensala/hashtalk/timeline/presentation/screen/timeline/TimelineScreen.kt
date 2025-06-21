package com.odensala.hashtalk.timeline.presentation.screen.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Timestamp
import com.odensala.hashtalk.R
import com.odensala.hashtalk.core.presentation.components.CenteredContent
import com.odensala.hashtalk.core.presentation.components.LoadingButton
import com.odensala.hashtalk.timeline.presentation.components.LogoutDialog
import com.odensala.hashtalk.timeline.presentation.error.PostUiError

@Composable
fun TimelineScreen(
    viewModel: TimelineViewModel = hiltViewModel(),
    onNavigateToAddPost: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    TimelineContent(
        uiState = uiState,
        onLogout = { showLogoutDialog = true },
        onNavigateToAddPost = onNavigateToAddPost,
    )

    LogoutDialog(
        showDialog = showLogoutDialog,
        onConfirm = {
            showLogoutDialog = false
            viewModel.logout()
        },
        onDismiss = { showLogoutDialog = false },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineContent(
    uiState: TimelineUiState,
    modifier: Modifier = Modifier,
    onLogout: () -> Unit = {},
    onNavigateToAddPost: () -> Unit = {},
) {
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        if (uiState.posts.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.timeline)) },
                actions = {
                    LoadingButton(
                        onClick = onLogout,
                        text = stringResource(R.string.logout),
                        isLoading = uiState.isLoggingOut,
                        enabled = !uiState.isLoggingOut,
                        modifier =
                            Modifier.padding(
                                end = 8.dp,
                            ),
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToAddPost() },
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_post))
            }
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            when {
                uiState.isLoading -> {
                    CenteredContent {
                        CircularProgressIndicator()
                    }
                }

                uiState.posts.isEmpty() -> {
                    CenteredContent {
                        Text(stringResource(R.string.empty_posts))
                    }
                }

                uiState.error != null -> {
                    CenteredContent {
                        Text(
                            text =
                                when (uiState.error) {
                                    PostUiError.Unauthorized -> stringResource(R.string.unauthorized_error)
                                    PostUiError.Unavailable -> stringResource(R.string.unavailable_error)
                                    PostUiError.Unknown -> stringResource(R.string.unknown_error)
                                },
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(uiState.posts, key = { it.id }) { post ->
                            PostItem(post = post)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimelineScreen() {
    TimelineContent(
        uiState =
            TimelineUiState(
                isLoading = false,
                posts =
                    listOf(
                        PostUiModel(
                            id = "1",
                            userEmail = "test@example.com",
                            content = "Hello, world!",
                            timestamp = Timestamp.now(),
                        ),
                    ),
            ),
        onNavigateToAddPost = {},
    )
}
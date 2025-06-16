package com.odensala.hashtalk.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.odensala.hashtalk.auth.domain.model.AuthState
import com.odensala.hashtalk.auth.presentation.navigation.AuthGraph
import com.odensala.hashtalk.auth.presentation.navigation.authNavGraph
import com.odensala.hashtalk.timeline.presentation.navigation.TimelineGraph
import com.odensala.hashtalk.timeline.presentation.navigation.timelineNavGraph

@Composable
fun HashTalkApp(authViewModel: AppViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.collectAsState()

    val startDestination: Any =
        when (authState) {
            AuthState.Authenticated -> TimelineGraph
            AuthState.Unauthenticated -> AuthGraph
            AuthState.CheckingAuthentication -> AuthGraph // TODO: Can display a splash screen here instead for better UX
        }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        authNavGraph(navController)
        timelineNavGraph(navController)
    }
}
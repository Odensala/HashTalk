package com.odensala.hashtalk.timeline.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.odensala.hashtalk.timeline.presentation.screen.TimelineScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.timelineNavGraph(navController: NavController) {
    navigation<TimelineGraph>(startDestination = TimelineGraph.TimelineScreen) {
        composable<TimelineGraph.TimelineScreen> {
            TimelineScreen()
        }
    }
}

@Serializable
object TimelineGraph {
    @Serializable
    object TimelineScreen
}
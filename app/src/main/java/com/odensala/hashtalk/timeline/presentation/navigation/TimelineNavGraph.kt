package com.odensala.hashtalk.timeline.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.odensala.hashtalk.timeline.presentation.screen.post.AddPostScreen
import com.odensala.hashtalk.timeline.presentation.screen.timeline.TimelineScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.timelineNavGraph(navController: NavController) {
    navigation<TimelineGraph>(startDestination = TimelineGraph.TimelineScreen) {
        composable<TimelineGraph.TimelineScreen> {
            TimelineScreen(onNavigateToAddPost = { navController.navigate(TimelineGraph.AddPostScreen) })
        }
        composable<TimelineGraph.AddPostScreen> {
            AddPostScreen(onNavigateBack = { navController.navigateUp() })
        }
    }
}

@Serializable
object TimelineGraph {
    @Serializable
    object TimelineScreen

    @Serializable
    object AddPostScreen
}
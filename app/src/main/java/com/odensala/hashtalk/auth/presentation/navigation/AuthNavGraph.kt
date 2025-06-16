package com.odensala.hashtalk.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.odensala.hashtalk.auth.presentation.screen.login.LoginScreen
import com.odensala.hashtalk.auth.presentation.screen.signup.SignUpScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation<AuthGraph>(startDestination = AuthGraph.LoginScreen) {
        composable<AuthGraph.LoginScreen> {
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate(AuthGraph.SignUpScreen)
                },
            )
        }

        composable<AuthGraph.SignUpScreen> {
            SignUpScreen()
        }
    }
}

@Serializable
object AuthGraph {
    @Serializable
    object LoginScreen

    @Serializable
    object SignUpScreen
}
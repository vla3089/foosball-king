package com.vladds.foosballking.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vladds.foosballking.presentation.login.LoginScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login
    ) {
        composable(route = AuthScreen.Login) {
            LoginScreen {
                navController.popBackStack()
                navController.navigate(Graph.HOME)
            }
        }
    }
}

object AuthScreen {
    const val Login = "/login"
}

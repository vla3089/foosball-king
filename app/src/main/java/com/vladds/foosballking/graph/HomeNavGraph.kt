package com.vladds.foosballking.graph

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vladds.foosballking.R
import com.vladds.foosballking.presentation.history.GameHistoryRecordDetailsScreen
import com.vladds.foosballking.presentation.history.HistoryScreen
import com.vladds.foosballking.presentation.player.PickPlayerScreen
import com.vladds.foosballking.presentation.ranking.RankingScreen

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController,
        startDestination = Screen.Ranking.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Ranking.route) { RankingScreen() }
        composable(Screen.History.route) {
            HistoryScreen { record ->
                navController.navigate("history/${record.id}")
            }
        }
        composable("history/add") {
            GameHistoryRecordDetailsScreen(navController) { key ->
                navController.navigate("pick_player/$key")
            }
        }
        composable("history/{recordId}",
            arguments = listOf(
                navArgument("recordId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            GameHistoryRecordDetailsScreen(
                navController, backStackEntry.arguments!!.getLong("recordId")
            ) { key ->
                navController.navigate("pick_player/$key")
            }
        }
        composable("pick_player/{playerKey}",
            arguments = listOf(
                navArgument("playerKey") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            PickPlayerScreen { player ->
                val key = backStackEntry.arguments!!.getString("playerKey")!!
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    key,
                    player.id
                )
                navController.navigateUp()
            }
        }
    }
}

sealed class Screen(
    val route: String, @StringRes val resourceId: Int, @DrawableRes val iconRes: Int
) {
    object Ranking : Screen("ranking", R.string.ranking, R.drawable.ic_baseline_bar_chart_24)

    object History : Screen("history", R.string.history, R.drawable.ic_baseline_history_24)
}

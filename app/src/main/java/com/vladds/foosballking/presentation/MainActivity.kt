package com.vladds.foosballking.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vladds.foosballking.R
import com.vladds.foosballking.core.presentation.collectAsStateLifecycleAware
import com.vladds.foosballking.presentation.history.GameHistoryRecordDetailsScreen
import com.vladds.foosballking.presentation.history.HistoryScreen
import com.vladds.foosballking.presentation.player.PickPlayerScreen
import com.vladds.foosballking.presentation.ranking.RankingScreen

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
            val systemInDarkTheme = isSystemInDarkTheme()
            val colorScheme = when {
                dynamicColor && systemInDarkTheme -> dynamicDarkColorScheme(LocalContext.current)
                dynamicColor -> dynamicLightColorScheme(LocalContext.current)
                systemInDarkTheme -> darkColorScheme()
                else -> lightColorScheme()
            }

            MaterialTheme(
                colorScheme = colorScheme
            ) {
                val navController = rememberNavController()
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.app_name)) },
                    )
                },
                    floatingActionButton = {
                        FoosballKingFab(navController)
                    },
                    bottomBar = {
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            val items = listOf(Screen.Ranking, Screen.History)
                            items.forEach { screen ->
                                NavigationBarItem(icon = {
                                    Icon(
                                        painterResource(id = screen.iconRes),
                                        contentDescription = null
                                    )
                                },
                                    label = { Text(stringResource(screen.resourceId)) },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    })
                            }
                        }
                    }) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Screen.Ranking.route,
                        Modifier.padding(innerPadding)
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
            }
        }
    }
}

@Composable
fun FoosballKingFab(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryFlow.collectAsStateLifecycleAware(null)
    val route = backStackEntry?.destination?.route
    if (route == Screen.History.route) {
        FloatingActionButton(onClick = {
            navController.navigate("history/add")
        }) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = ""
            )
        }
    }
}

sealed class Screen(
    val route: String, @StringRes val resourceId: Int, @DrawableRes val iconRes: Int
) {
    object Ranking : Screen("ranking", R.string.ranking, R.drawable.ic_baseline_bar_chart_24)

    object History : Screen("history", R.string.history, R.drawable.ic_baseline_history_24)
}

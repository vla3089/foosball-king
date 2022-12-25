package com.vladds.foosballking.presentation.home

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vladds.foosballking.R
import com.vladds.foosballking.graph.HomeNavGraph
import com.vladds.foosballking.graph.Screen
import com.vladds.foosballking.presentation.FoosballKingFab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
            )
        },
        floatingActionButton = { FoosballKingFab(navController) },
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        HomeNavGraph(navController = navController, innerPadding)
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
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
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                })
        }
    }
}
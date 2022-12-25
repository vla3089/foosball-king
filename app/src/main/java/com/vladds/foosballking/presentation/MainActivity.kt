package com.vladds.foosballking.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vladds.foosballking.core.presentation.collectAsStateLifecycleAware
import com.vladds.foosballking.graph.RootNavigationGraph
import com.vladds.foosballking.graph.Screen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = !isSystemInDarkTheme()
            )

            FoosballTheme {
                RootNavigationGraph(navController = rememberNavController())
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

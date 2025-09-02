package com.example.meditationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.meditationapp.ui.theme.MeditationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeditationAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(navController)
                    }
                    composable(Screen.Timer.route) {
                        Screen2(navController)
                    }
                    composable("timer/{timeInMinutes}") { backStackEntry ->
                        val timeInMinutes = backStackEntry.arguments?.
                        getString("timeInMinutes")?.
                        toIntOrNull() ?: 3
                        TimerScreen(totalTimeInMillis = timeInMinutes * 60 * 1000L)
                    }
                }
            }
        }
    }
}
package com.example.meditationapp

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Timer : Screen("timer")
}
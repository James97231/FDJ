package com.example.basictest

sealed class Screen(
    val route: String,
) {
    object TeamListScreen : Screen("team_list_screen")
}

package com.example.basictest

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.basictest.ui.team.TeamListRoute

@Composable
fun setupNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(navController = navController, startDestination = Screen.TeamListScreen.route) {
        composable(route = Screen.TeamListScreen.route) { TeamListRoute(modifier = modifier) }
    }
}

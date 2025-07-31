package com.example.basictest.ui.team

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Route for the team list screen.
 *
 * @param modifier Modifier
 * @param viewModel TeamListViewModel
 */
@Composable
fun TeamListRoute(
    modifier: Modifier = Modifier,
    viewModel: TeamListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    TeamListScreen(
        uiState = uiState,
        modifier = modifier,
        actionItemSelected = { viewModel.handler((TeamListAction.LeagueSelected(it))) },
        actionQueryChanged = { viewModel.handler(TeamListAction.QueryChanged(it)) },
        actionClearClicked = { viewModel.handler(TeamListAction.QueryChanged("")) },
    )
}

package com.example.basictest.ui.team

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

    val actionItemSelected = remember<(String) -> Unit> {
        { leagueName -> viewModel.handler(TeamListAction.LeagueSelected(leagueName)) }
    }

    val actionQueryChanged = remember<(String) -> Unit> {
        { newQuery -> viewModel.handler(TeamListAction.QueryChanged(newQuery)) }
    }

    val actionClearClicked = remember<() -> Unit> {
        { viewModel.handler(TeamListAction.QueryChanged("")) }
    }
    TeamListScreen(
        uiState = uiState,
        modifier = modifier,
        actionItemSelected = actionItemSelected,
        actionQueryChanged = actionQueryChanged,
        actionClearClicked = actionClearClicked,
    )
}

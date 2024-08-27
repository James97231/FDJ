package com.example.basictest.ui.team

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.basictest.R
import com.example.basictest.domain.model.Team
import com.example.basictest.ui.components.AnomalyListComponent
import com.example.basictest.ui.components.AnomalyType
import com.example.basictest.ui.components.AutoCompleteTextField
import com.example.basictest.ui.theme.BasicTestTheme

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

/**
 * Team list screen.
 *
 * @param uiState TeamListUIState
 * @param modifier Modifier
 * @param actionItemSelected (String) -> Unit
 * @param actionQueryChanged (String) -> Unit
 * @param actionClearClicked () -> Unit
 */
@Composable
fun TeamListScreen(
    uiState: TeamListUIState,
    modifier: Modifier = Modifier,
    actionItemSelected: (String) -> Unit = { },
    actionQueryChanged: (String) -> Unit = { },
    actionClearClicked: () -> Unit = { },
) {
    Column(modifier = modifier) {
        // Autocomplete TextField
        AutoCompleteTextField(
            query = uiState.query,
            suggestions = uiState.suggestions,
            placeholder = stringResource(id = R.string.search_league_placeholder),
            actionItemSelected = actionItemSelected,
            actionQueryChanged = actionQueryChanged,
            actionClearClicked = actionClearClicked,
            modifier = Modifier.animateContentSize(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (uiState) {
            is TeamListUIState.Success -> {
                // Afficher la liste des équipes
                TeamBadgeGrid(teams = uiState.teams, modifier = Modifier.fillMaxSize())
            }

            is TeamListUIState.Loading -> {
                // Afficher un indicateur de chargement
                AnomalyListComponent(
                    anomalyType = AnomalyType.LOADING,
                    message = stringResource(id = R.string.loading),
                    modifier = Modifier.fillMaxSize(),
                    painter = rememberVectorPainter(Icons.Default.Search),
                )
            }

            is TeamListUIState.EmptyData -> {
                // Afficher un composant indiquant que la liste est vide
                AnomalyListComponent(
                    anomalyType = AnomalyType.EMPTY,
                    message = stringResource(id = R.string.empty_team_list_message),
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    painter = rememberVectorPainter(Icons.Default.Search),
                )
            }

            is TeamListUIState.Error -> {
                // Afficher un message d'erreur
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        // Message Text
                        AnomalyListComponent(
                            anomalyType = AnomalyType.ERROR,
                            message =
                                (
                                    uiState.message
                                        ?: stringResource(id = R.string.error_message)
                                ) + "\n\n" +
                                    stringResource(
                                        id = R.string.restart_error_message,
                                    ),
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                            painter = rememberVectorPainter(Icons.Default.Warning),
                        )
                    }
                }
            }
        }
    }
}

@Preview("Success", "state")
@Composable
private fun TeamListScreenSuccessPreview() {
    BasicTestTheme {
        TeamListScreen(
            TeamListUIState.Success(
                teams =
                    listOf(
                        Team(
                            "1",
                            name = "Paris Saint-Germain",
                            strBadge = "https://www.thesportsdb.com/images/media/team/badge/rwqrrq1473504808.png",
                        ),
                        Team(
                            "2",
                            name = "Olympique de Marseille",
                            strBadge = "https://www.thesportsdb.com/images/media/team/badge/uutsyt1473504764.png",
                        ),
                        Team(
                            "3",
                            name = "Olympique Lyonnais",
                            strBadge = "https://www.thesportsdb.com/images/media/team/badge/blk9771656932845.png",
                        ),
                        Team(
                            "4",
                            name = "AS Monaco",
                            strBadge = "https://www.thesportsdb.com/images/media/team/badge/exjf5l1678808044.png",
                        ),
                        Team(
                            "5",
                            name = "AS Saint-Étienne",
                            strBadge = "https://www.thesportsdb.com/images/media/team/badge/m4ej831656423694.png",
                        ),
                        Team(
                            "6",
                            name = "FC Nantes",
                            strBadge = "https://www.thesportsdb.com/images/media/team/badge/mla9x61678808018.png",
                        ),
                        Team(
                            "7",
                            name = "Stade Rennais FC",
                            strBadge = "https://www.thesportsdb.com/images/media/team/badge/ypturx1473504818.png",
                        ),
                    ),
                query = "French Ligue 1",
                suggestions = emptyList(),
            ),
        )
    }
}

@Preview("Loading", "state")
@Composable
private fun TeamListScreenLoadingPreview() {
    BasicTestTheme {
        TeamListScreen(TeamListUIState.Loading())
    }
}

@Preview("Empty Data", "state")
@Composable
private fun TeamListScreenEmptyDataPreview() {
    BasicTestTheme {
        TeamListScreen(TeamListUIState.EmptyData())
    }
}

@Preview("Error", "state")
@Composable
private fun TeamListScreenErrorPreview() {
    BasicTestTheme {
        TeamListScreen(TeamListUIState.Error("Error message"))
    }
}

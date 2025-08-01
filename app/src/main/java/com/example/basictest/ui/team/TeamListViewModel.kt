package com.example.basictest.ui.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basictest.di.IoDispatcher
import com.example.basictest.domain.usecase.GetAllLeaguesUseCase
import com.example.basictest.domain.usecase.GetTeamsByLeagueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamListViewModel
    @Inject
    constructor(
        private val getAllLeaguesUseCase: GetAllLeaguesUseCase,
        private val getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(TeamListUIState())
        val uiState: StateFlow<TeamListUIState> = _uiState.asStateFlow()

        private var searchJob: Job? = null

        init {
            fetchLeagues()
        }

        /**
         * Handles the query change event.
         * Updates the UI state with the new query and filters suggestions based on it.
         */
        val onQueryChanged: (String) -> Unit = { newQuery ->
            _uiState.update { currentState ->
                val newSuggestions =
                    if (newQuery.isBlank()) {
                        emptyList()
                    } else {
                        currentState.leagues
                            .filter { it.contains(newQuery, ignoreCase = true) }
                            .take(5)
                    }

                currentState.copy(
                    query = newQuery,
                    suggestions = newSuggestions.toImmutableList(),
                    teams = persistentListOf(),
                    status = ScreenStatus.IDLE,
                )
            }
        }

        /**
         * Handles the clear button click event.
         * Resets the query and suggestions, and clears the teams list.
         */
        val onClearClicked: () -> Unit = { onQueryChanged("") }

        /**
         * Handles the league selection event.
         * Cancels any ongoing search job and fetches teams for the selected league.
         */
        val onLeagueSelected: (String) -> Unit = { leagueName ->
            searchJob?.cancel()
            searchJob =
                viewModelScope.launch(ioDispatcher) {
                    _uiState.update { it.copy(query = leagueName, suggestions = persistentListOf(), status = ScreenStatus.LOADING) }

                    getTeamsByLeagueUseCase(leagueName)
                        .onSuccess { teams ->
                            _uiState.update {
                                it.copy(
                                    teams = teams.toImmutableList(),
                                    status = if (teams.isEmpty()) ScreenStatus.IDLE else ScreenStatus.SUCCESS,
                                )
                            }
                        }.onFailure { error ->
                            _uiState.update {
                                it.copy(status = ScreenStatus.ERROR, errorMessage = error.message)
                            }
                        }
                }
        }

        private fun fetchLeagues() {
            viewModelScope.launch(ioDispatcher) {
                _uiState.update { it.copy(status = ScreenStatus.LOADING) }
                getAllLeaguesUseCase()
                    .onSuccess { leagues ->
                        _uiState.update {
                            it.copy(
                                leagues = leagues.map { league -> league.name }.toImmutableList(),
                                status = ScreenStatus.IDLE,
                            )
                        }
                    }.onFailure { error ->
                        _uiState.update {
                            it.copy(status = ScreenStatus.ERROR, errorMessage = error.message)
                        }
                    }
            }
        }
    }

package com.example.basictest.ui.team

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basictest.domain.model.Team
import com.example.basictest.domain.usecase.GetAllLeaguesUseCase
import com.example.basictest.domain.usecase.GetTeamsByLeagueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamListViewModel
    @Inject
    constructor(
        private val getAllLeaguesUseCase: GetAllLeaguesUseCase,
        private val getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase,
    ) : ViewModel() {
        private val leagues = MutableStateFlow<List<String>>(emptyList())
        private val teams = MutableStateFlow<List<Team>>(emptyList())

        private var queryJob: Job? = null

        var query = ""

        var suggestions = emptyList<String>()

        var uiState = MutableStateFlow<TeamListUIState>(TeamListUIState.EmptyData())
            private set

        init {
            viewModelScope.launch(Dispatchers.IO) {
                fetchLeagues()
            }
        }

        fun handler(action: TeamListAction) {
            when (action) {
                is TeamListAction.QueryChanged -> {
                    queryJob?.cancel()
                    queryJob = viewModelScope.launch(Dispatchers.IO) { onQueryChanged(action.newQuery) }
                }

                is TeamListAction.LeagueSelected ->
                    viewModelScope.launch(Dispatchers.IO) {
                        onLeagueSelected(
                            action.leagueName,
                        )
                    }
            }
        }

        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        suspend fun fetchLeagues() {
            uiState.value = TeamListUIState.Loading(query = query, suggestions = emptyList())
            getAllLeaguesUseCase()
                .onSuccess { leagues ->
                    this@TeamListViewModel.leagues.value = leagues.map { it.name }
                    uiState.value =
                        TeamListUIState.EmptyData(query = query, suggestions = emptyList())
                }.onFailure {
                    uiState.value = TeamListUIState.Error(it.message)
                }
        }

        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        suspend fun fetchTeamsByLeague(leagueName: String) {
            uiState.value = TeamListUIState.Loading(query = leagueName, suggestions = emptyList())
            getTeamsByLeagueUseCase(leagueName)
                .onSuccess {
                    teams.value = it
                    uiState.value = TeamListUIState.Success(teams.value, leagueName, emptyList())
                }.onFailure {
                    uiState.value =
                        TeamListUIState.Error(
                            it.message,
                            query = leagueName,
                            suggestions = emptyList(),
                        )
                }
        }

        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        suspend fun onQueryChanged(newQuery: String) {
            if (query == newQuery) return
            query = newQuery

            suggestions =
                leagues.value
                    .filter { it.contains(newQuery, ignoreCase = true) }
                    .take(5)

            if (queryJob?.isActive == false) return

            uiState.value =
                TeamListUIState.EmptyData(query = query, suggestions = suggestions)
        }

        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        suspend fun onLeagueSelected(leagueName: String) {
            query = leagueName
            fetchTeamsByLeague(leagueName)
        }
    }

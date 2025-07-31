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
    /*private val leagues = MutableStateFlow<List<String>>(emptyList())
    private val teams = MutableStateFlow<List<Team>>(emptyList())

    private var queryJob: Job? = null

    var query = ""

    var suggestions = emptyList<String>()

    var uiState = MutableStateFlow<TeamListUIState>(TeamListUIState.EmptyData())
        private set


    private val _uiState = MutableStateFlow(TeamListUIState2())
    val uiState2: StateFlow<TeamListUIState2> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            fetchLeagues()
        }
    }

    fun handler(action: TeamListAction) {
        when (action) {
            is TeamListAction.QueryChanged -> {
                queryJob?.cancel()
                queryJob = viewModelScope.launch(ioDispatcher) { onQueryChanged(action.newQuery) }
            }

            is TeamListAction.LeagueSelected ->
                viewModelScope.launch(ioDispatcher) {
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
    fun onQueryChanged(newQuery: String) {
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
*/

        private val _uiState = MutableStateFlow(TeamListUIState())
        val uiState: StateFlow<TeamListUIState> = _uiState.asStateFlow()

        private var searchJob: Job? = null

        init {
            fetchLeagues()
        }

        fun handler(action: TeamListAction) {
            when (action) {
                is TeamListAction.QueryChanged -> onQueryChanged(action.newQuery)
                is TeamListAction.LeagueSelected -> onLeagueSelected(action.leagueName)
            }
        }

        private fun onQueryChanged(newQuery: String) {
            _uiState.update { currentState ->
                val newSuggestions =
                    if (newQuery.isBlank()) {
                        emptyList()
                    } else {
                        currentState.leagues
                            .filter { it.contains(newQuery, ignoreCase = true) }
                            .take(5)
                    }
                // Utilisation de copy() pour une mise à jour immuable et atomique
                currentState.copy(query = newQuery, suggestions = newSuggestions.toImmutableList())
            }
        }

        private fun onLeagueSelected(leagueName: String) {
            searchJob?.cancel()
            searchJob =
                viewModelScope.launch(ioDispatcher) {
                    _uiState.update { it.copy(query = leagueName, suggestions = persistentListOf(), status = ScreenStatus.LOADING) }

                    getTeamsByLeagueUseCase(leagueName)
                        .onSuccess { teams ->
                            _uiState.update {
                                it.copy(
                                    teams = teams.toImmutableList(),
                                    status = if (teams.isEmpty()) ScreenStatus.IDLE else ScreenStatus.SUCCESS, // ou un état Empty
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

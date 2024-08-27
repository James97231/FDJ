package com.example.basictest.ui.team

/**
 * Sealed interface representing different UI events for the Team List screen.
 * Use to communicate between UI and Viewmodel.
 */
sealed class TeamListAction {
    data class QueryChanged(
        val newQuery: String,
    ) : TeamListAction()

    data class LeagueSelected(
        val leagueName: String,
    ) : TeamListAction()
}

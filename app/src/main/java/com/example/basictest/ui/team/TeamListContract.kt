package com.example.basictest.ui.team

import androidx.compose.runtime.Immutable
import com.example.basictest.domain.model.Team
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

enum class ScreenStatus { IDLE, LOADING, SUCCESS, ERROR }

@Immutable
data class TeamListUIState(
    val query: String = "",
    val suggestions: ImmutableList<String> = persistentListOf(),
    val teams: ImmutableList<Team> = persistentListOf(),
    val leagues: ImmutableList<String> = persistentListOf(), // La liste complète des ligues est aussi un état
    val status: ScreenStatus = ScreenStatus.IDLE,
    val errorMessage: String? = null,
)

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

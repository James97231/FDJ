package com.example.basictest.ui.team

import com.example.basictest.domain.model.Team

/**
 * Sealed class representing different UI states for the Team List screen.
 */
sealed class TeamListUIState(
    open var query: String,
    open val suggestions: List<String>,
) {
    data class Loading(
        override var query: String = "",
        override val suggestions: List<String> = emptyList(),
    ) : TeamListUIState(query, suggestions)

    data class Success(
        val teams: List<Team>,
        override var query: String = "",
        override val suggestions: List<String> = emptyList(),
    ) : TeamListUIState(query, suggestions)

    data class EmptyData(
        override var query: String = "",
        override val suggestions: List<String> = emptyList(),
    ) : TeamListUIState(query, suggestions)

    data class Error(
        val message: String?,
        override var query: String = "",
        override val suggestions: List<String> = emptyList(),
    ) : TeamListUIState(query, suggestions)
}

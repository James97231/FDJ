package com.example.basictest.domain.usecase

import com.example.basictest.domain.repository.SportsRepository

class GetTeamsByLeagueUseCase(
    private val repository: SportsRepository,
) {
    suspend operator fun invoke(leagueName: String) =
        repository
            .getTeamsByLeague(leagueName)
            .map { teams ->
                teams
                    .sortedByDescending { it.name }
                    .filterIndexed { index, _ -> index % 2 == 0 }
            }
}

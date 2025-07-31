package com.example.basictest.data.repository

import com.example.basictest.data.mapper.toDomain
import com.example.basictest.data.model.LeagueDto
import com.example.basictest.data.model.TeamDto
import com.example.basictest.data.remote.SportsApi
import com.example.basictest.domain.repository.SportsRepository

class SportsRepositoryImpl(
    private val api: SportsApi,
) : SportsRepository {
    override suspend fun getAllLeagues() =
        runCatching {
            val response = api.getAllLeagues()
            response.leagues.map(LeagueDto::toDomain)
        }

    override suspend fun getTeamsByLeague(leagueName: String) =
        runCatching {
            val response = api.getTeamsByLeague(leagueName)
            response.teams.map(TeamDto::toDomain)
        }
}

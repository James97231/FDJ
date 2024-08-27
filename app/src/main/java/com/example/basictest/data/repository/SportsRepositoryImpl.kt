package com.example.basictest.data.repository

import com.example.basictest.data.mapper.LeagueMapper
import com.example.basictest.data.mapper.TeamMapper
import com.example.basictest.data.remote.SportsApi
import com.example.basictest.domain.model.Team
import com.example.basictest.domain.repository.SportsRepository

class SportsRepositoryImpl(
    private val api: SportsApi,
) : SportsRepository {
    override suspend fun getAllLeagues() =
        runCatching {
            val response = api.getAllLeagues()
            response.leagues.map(LeagueMapper::fromDto)
        }

    override suspend fun getTeamsByLeague(leagueName: String) =
        runCatching {
            val response = api.getTeamsByLeague(leagueName)
            response.teams.map(TeamMapper::fromDto)
        }
}

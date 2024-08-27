package com.example.basictest.domain.repository

import com.example.basictest.domain.model.League
import com.example.basictest.domain.model.Team

interface SportsRepository {
    suspend fun getAllLeagues(): Result<List<League>>

    suspend fun getTeamsByLeague(leagueName: String): Result<List<Team>>
}

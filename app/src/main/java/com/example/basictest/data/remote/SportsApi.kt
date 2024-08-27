package com.example.basictest.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface SportsApi {
    @GET("all_leagues.php")
    suspend fun getAllLeagues(): LeaguesResponse

    @GET("search_all_teams.php")
    suspend fun getTeamsByLeague(
        @Query("l") leagueName: String,
    ): TeamsResponse
}

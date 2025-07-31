package com.example.basictest.data.model

import com.google.gson.annotations.SerializedName

data class LeagueDto(
    @SerializedName("idLeague") val idLeague: String,
    @SerializedName("strLeague") val strLeague: String,
    @SerializedName("strSport") val strSport: String?,
    @SerializedName("strLeagueAlternate") val strLeagueAlternate: String? = null,
)

data class LeaguesResponse(
    @SerializedName("leagues") val leagues: List<LeagueDto>,
)

package com.example.basictest.data.model

import com.google.gson.annotations.SerializedName

data class TeamDto(
    @SerializedName("idTeam") val idTeam: String,
    @SerializedName("strTeam") val strTeam: String,
    @SerializedName("strBadge") val strBadge: String,
)

data class TeamsResponse(
    @SerializedName("teams") val teams: List<TeamDto>,
)

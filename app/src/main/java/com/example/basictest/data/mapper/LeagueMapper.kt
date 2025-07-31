package com.example.basictest.data.mapper

import com.example.basictest.data.model.LeagueDto
import com.example.basictest.domain.model.League

/**
 * Extension function to convert LeagueDto to League.
 */
fun LeagueDto.toDomain(): League =
    League(
        id = idLeague,
        name = strLeague,
        sport = strSport,
    )

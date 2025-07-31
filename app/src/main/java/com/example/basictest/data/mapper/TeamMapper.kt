package com.example.basictest.data.mapper

import com.example.basictest.data.model.TeamDto
import com.example.basictest.domain.model.Team

/**
 * Extension function to convert TeamDto to Team.
 */
fun TeamDto.toDomain(): Team =
    Team(
        id = idTeam,
        name = strTeam,
        strBadge = strBadge,
    )

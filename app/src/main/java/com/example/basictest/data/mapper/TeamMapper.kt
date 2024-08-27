package com.example.basictest.data.mapper

import com.example.basictest.data.remote.TeamDto
import com.example.basictest.domain.model.Team

object TeamMapper {
    /**
     * Transforme un TeamDto en Team (du DTO vers le domaine)
     */
    fun fromDto(dto: TeamDto): Team =
        Team(
            id = dto.idTeam,
            name = dto.strTeam,
            strBadge = dto.strBadge,
        )
}

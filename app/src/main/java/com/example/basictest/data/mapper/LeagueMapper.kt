package com.example.basictest.data.mapper

import com.example.basictest.data.remote.LeagueDto
import com.example.basictest.domain.model.League

object LeagueMapper {
    // Transforme un LeagueDto en League (du DTO vers le domaine)
    fun fromDto(dto: LeagueDto): League =
        League(
            id = dto.idLeague,
            name = dto.strLeague,
            sport = dto.strSport,
        )
}

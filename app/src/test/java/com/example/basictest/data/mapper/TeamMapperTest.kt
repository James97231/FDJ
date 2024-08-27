package com.example.basictest.data.mapper

import com.example.basictest.data.remote.TeamDto
import com.example.basictest.domain.model.Team
import org.junit.Assert.assertEquals
import org.junit.Test

class TeamMapperTest {
    @Test
    fun fromDtoMapsCorrectly() {
        val dto = TeamDto("1", strTeam = "Team1", strBadge = "Badge1")
        val expected = Team("1", "Team1", "Badge1")

        val result = TeamMapper.fromDto(dto)

        assertEquals(expected, result)
    }
}
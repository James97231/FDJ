package com.example.basictest.data.mapper

import com.example.basictest.data.remote.LeagueDto
import com.example.basictest.domain.model.League
import org.junit.Assert.assertEquals
import org.junit.Test

class LeagueMapperTest {
    @Test
    fun fromDtoMapsCorrectly() {
        val dto = LeagueDto("1", "League1", strSport = "Sport1")
        val expected = League("1", "League1", "Sport1")

        val result = LeagueMapper.fromDto(dto)

        assertEquals(expected, result)
    }
}

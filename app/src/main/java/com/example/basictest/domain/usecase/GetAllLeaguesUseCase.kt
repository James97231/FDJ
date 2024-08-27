package com.example.basictest.domain.usecase

import com.example.basictest.domain.model.League
import com.example.basictest.domain.repository.SportsRepository

class GetAllLeaguesUseCase(
    private val repository: SportsRepository,
) {
    suspend operator fun invoke(): Result<List<League>> = repository.getAllLeagues()
}

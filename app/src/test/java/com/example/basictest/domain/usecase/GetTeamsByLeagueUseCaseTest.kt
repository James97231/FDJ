import com.example.basictest.domain.model.Team
import com.example.basictest.domain.repository.SportsRepository
import com.example.basictest.domain.usecase.GetTeamsByLeagueUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetTeamsByLeagueUseCaseTest {
    private lateinit var sportsRepository: SportsRepository
    private lateinit var getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase

    @Before
    fun setUp() {
        sportsRepository = mockk()
        getTeamsByLeagueUseCase = GetTeamsByLeagueUseCase(sportsRepository)
    }

    @Test
    fun getTeamsByLeagueReturnsSortedAndFilteredTeams() =
        runBlocking {
            val leagueName = "League1"
            val teams =
                runCatching {
                    listOf(
                        Team("Team1", name = "strTeam1"),
                        Team("Team2", name = "strTeam2"),
                        Team("Team3", name = "strTeam3"),
                        Team("Team4", name = "strTeam4"),
                    )
                }
            val expectedTeams =
                runCatching {
                    listOf(
                        Team("Team4", name = "strTeam4"),
                        Team("Team2", name = "strTeam2"),
                    )
                }
            coEvery { sportsRepository.getTeamsByLeague(leagueName) } returns teams
            val result = getTeamsByLeagueUseCase(leagueName)
            assertEquals(expectedTeams, result)
        }

    @Test
    fun getTeamsByLeagueReturnsEmptyList() =
        runBlocking {
            val leagueName = "League1"
            val teams = runCatching { emptyList<Team>() }
            coEvery { sportsRepository.getTeamsByLeague(leagueName) } returns teams
            val result = getTeamsByLeagueUseCase(leagueName)
            assertEquals(teams, result)
        }

    @Test
    fun getTeamsByLeagueHandlesException(): Unit =
        runBlocking {
            val leagueName = "League1"
            coEvery { sportsRepository.getTeamsByLeague(leagueName) } throws Exception("Network error")
            try {
                getTeamsByLeagueUseCase(leagueName)
            } catch (e: Exception) {
                assertEquals("Network error", e.message)
            }
        }
}

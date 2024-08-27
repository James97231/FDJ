import com.example.basictest.domain.model.League
import com.example.basictest.domain.repository.SportsRepository
import com.example.basictest.domain.usecase.GetAllLeaguesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAllLeaguesUseCaseTest {
    private lateinit var sportsRepository: SportsRepository
    private lateinit var getAllLeaguesUseCase: GetAllLeaguesUseCase

    @Before
    fun setUp() {
        sportsRepository = mockk()
        getAllLeaguesUseCase = GetAllLeaguesUseCase(sportsRepository)
    }

    @Test
    fun getAllLeaguesReturnsListOfLeagues() =
        runBlocking {
            val leagues =
                runCatching {
                    listOf(
                        League("League1", "strLeague1"),
                        League("League2", "strLeague2"),
                    )
                }
            coEvery { sportsRepository.getAllLeagues() } returns leagues
            val result = getAllLeaguesUseCase()
            assertEquals(leagues, result)
        }

    @Test
    fun getAllLeaguesReturnsEmptyList() =
        runBlocking {
            val leagues = runCatching { emptyList<League>() }
            coEvery { sportsRepository.getAllLeagues() } returns leagues
            val result = getAllLeaguesUseCase()
            assertEquals(leagues, result)
        }

    @Test
    fun getAllLeaguesHandlesException(): Unit =
        runBlocking {
            coEvery { sportsRepository.getAllLeagues() } throws Exception("Network error")
            try {
                getAllLeaguesUseCase()
            } catch (e: Exception) {
                assertEquals("Network error", e.message)
            }
        }
}

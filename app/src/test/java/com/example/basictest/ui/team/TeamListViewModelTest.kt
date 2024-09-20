import com.example.basictest.domain.model.League
import com.example.basictest.domain.model.Team
import com.example.basictest.domain.usecase.GetAllLeaguesUseCase
import com.example.basictest.domain.usecase.GetTeamsByLeagueUseCase
import com.example.basictest.ui.team.TeamListUIState
import com.example.basictest.ui.team.TeamListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TeamListViewModelTest {
    private lateinit var getAllLeaguesUseCase: GetAllLeaguesUseCase
    private lateinit var getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase
    private lateinit var viewModel: TeamListViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getAllLeaguesUseCase = mockk()
        getTeamsByLeagueUseCase = mockk()
        viewModel = TeamListViewModel(getAllLeaguesUseCase, getTeamsByLeagueUseCase,testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchLeaguesUpdatesLeaguesAndUIState() {
        runBlocking {
            val leagues =
                runCatching {
                    listOf(
                        League("League1", "strLeague1"),
                        League("League2", "strLeague2"),
                    )
                }
            coEvery { getAllLeaguesUseCase() } returns leagues
            viewModel.fetchLeagues()
            assertEquals(TeamListUIState.EmptyData(), viewModel.uiState.value)
        }
    }

    @Test
    fun fetchLeaguesHandlesException() =
        runBlocking {
            coEvery { getAllLeaguesUseCase() } returns Result.failure(Throwable("Network error"))
            try {
                viewModel.fetchLeagues()
            } catch (e: Exception) {
                assertEquals(TeamListUIState.Error("Network error"), viewModel.uiState.value)
            }
        }

    @Test
    fun fetchTeamsByLeagueUpdatesTeamsAndUIState() =
        runBlocking {
            val leagueName = "League1"
            val teams =
                listOf(Team("Team1", name = "strTeam1"), Team("Team2", name = "strTeam1"))
            coEvery { getTeamsByLeagueUseCase(leagueName) } returns runCatching { teams }
            viewModel.fetchTeamsByLeague(leagueName)
            assertEquals(
                TeamListUIState.Success(teams, leagueName, emptyList()),
                viewModel.uiState.value,
            )
        }

    @Test
    fun fetchTeamsByLeagueHandlesException() =
        runBlocking {
            val leagueName = "League1"
            coEvery { getTeamsByLeagueUseCase(leagueName) } returns Result.failure(Throwable("Network error"))
            try {
                viewModel.fetchTeamsByLeague(leagueName)
            } catch (e: Exception) {
                assertEquals(
                    TeamListUIState.Error(
                        "Network error",
                        query = leagueName,
                        suggestions = emptyList(),
                    ),
                    viewModel.uiState.value,
                )
            }
        }

    @Test
    fun onQueryChangedUpdatesQueryAndSuggestions() =
        runBlocking {
            val newQuery = "League"
            val leagues =
                runCatching {
                    listOf(
                        League("League1", "strLeague1"),
                        League("League2", "strLeague2"),
                    )
                }
            coEvery { getAllLeaguesUseCase() } returns leagues
            viewModel.fetchLeagues()
            viewModel.onQueryChanged(newQuery)
            assertEquals(newQuery, viewModel.query)
            assertEquals(listOf("strLeague1", "strLeague2"), viewModel.suggestions)
            assertEquals(
                TeamListUIState.EmptyData(
                    query = newQuery,
                    suggestions = listOf("strLeague1", "strLeague2"),
                ),
                viewModel.uiState.value,
            )
        }

    @Test
    fun onLeagueSelectedUpdatesQueryAndFetchesTeams() =
        runBlocking {
            val leagueName = "League1"
            val teams = listOf(Team("Team1"), Team("Team2"))
            coEvery { getTeamsByLeagueUseCase(leagueName) } returns runCatching { teams }
            viewModel.onLeagueSelected(leagueName)
            assertEquals(leagueName, viewModel.query)
            assertEquals(
                TeamListUIState.Success(teams, leagueName, emptyList()),
                viewModel.uiState.value,
            )
        }
}

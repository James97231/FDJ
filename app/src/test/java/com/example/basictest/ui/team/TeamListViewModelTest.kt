import com.example.basictest.domain.model.League
import com.example.basictest.domain.model.Team
import com.example.basictest.domain.usecase.GetAllLeaguesUseCase
import com.example.basictest.domain.usecase.GetTeamsByLeagueUseCase
import com.example.basictest.ui.team.ScreenStatus
import com.example.basictest.ui.team.TeamListAction
import com.example.basictest.ui.team.TeamListUIState
import com.example.basictest.ui.team.TeamListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TeamListViewModelTest {
    // Mocks pour les dépendances
    private lateinit var getAllLeaguesUseCase: GetAllLeaguesUseCase
    private lateinit var getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase

    // Le ViewModel à tester
    private lateinit var viewModel: TeamListViewModel

    // Un dispatcher de test pour exécuter les coroutines de manière synchrone
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        // Remplacement du dispatcher principal par notre dispatcher de test
        Dispatchers.setMain(testDispatcher)

        // Initialisation des mocks
        getAllLeaguesUseCase = mockk()
        getTeamsByLeagueUseCase = mockk()
    }

    @After
    fun tearDown() {
        // Restauration du dispatcher principal
        Dispatchers.resetMain()
    }

    @Test
    fun `init - when leagues fetch succeeds - updates state with leagues and idle status`() =
        runTest {
            // GIVEN: Le cas d'utilisation des ligues retourne une liste avec succès
            val leagues = listOf(League("1", "Ligue 1"), League("2", "Premier League"))
            coEvery { getAllLeaguesUseCase() } returns Result.success(leagues)

            // WHEN: Le ViewModel est initialisé
            viewModel = TeamListViewModel(getAllLeaguesUseCase, getTeamsByLeagueUseCase, testDispatcher)

            // THEN: L'état doit contenir les noms des ligues et le statut doit être IDLE
            val expectedState =
                TeamListUIState(
                    leagues = listOf("Ligue 1", "Premier League").toImmutableList(),
                    status = ScreenStatus.IDLE,
                )
            assertEquals(expectedState, viewModel.uiState.value)
        }

    @Test
    fun `init - when leagues fetch fails - updates state with error status`() =
        runTest {
            // GIVEN: Le cas d'utilisation des ligues retourne une erreur
            val errorMessage = "Network Error"
            coEvery { getAllLeaguesUseCase() } returns Result.failure(Exception(errorMessage))

            // WHEN: Le ViewModel est initialisé
            viewModel = TeamListViewModel(getAllLeaguesUseCase, getTeamsByLeagueUseCase, testDispatcher)

            // THEN: L'état doit refléter l'erreur
            val expectedState =
                TeamListUIState(
                    status = ScreenStatus.ERROR,
                    errorMessage = errorMessage,
                )
            assertEquals(expectedState, viewModel.uiState.value)
        }

    @Test
    fun `handler(LeagueSelected) - when teams fetch succeeds - updates state with teams and success status`() =
        runTest {
            // GIVEN: Le ViewModel est initialisé (on suppose que les ligues sont déjà chargées)
            coEvery { getAllLeaguesUseCase() } returns Result.success(emptyList())
            viewModel = TeamListViewModel(getAllLeaguesUseCase, getTeamsByLeagueUseCase, testDispatcher)

            val leagueName = "Ligue 1"
            val teams = listOf(Team("1", "PSG", "badge_url"))
            coEvery { getTeamsByLeagueUseCase(leagueName) } returns Result.success(teams)

            // WHEN: L'action de sélection d'une ligue est envoyée
            viewModel.handler(TeamListAction.LeagueSelected(leagueName))

            // THEN: L'état doit être mis à jour avec les équipes, le nom de la ligue et le statut SUCCESS
            val expectedState =
                TeamListUIState(
                    query = leagueName,
                    teams = teams.toImmutableList(),
                    status = ScreenStatus.SUCCESS,
                    leagues = persistentListOf(),
                    suggestions = persistentListOf(),
                )
            assertEquals(expectedState, viewModel.uiState.value)
        }

    @Test
    fun `handler(QueryChanged) - updates query and suggestions in state`() =
        runTest {
            // GIVEN: Le ViewModel est initialisé avec une liste de ligues
            val leagues = listOf(League("1", "French Ligue 1"), League("2", "English Premier League"))
            coEvery { getAllLeaguesUseCase() } returns Result.success(leagues)
            viewModel = TeamListViewModel(getAllLeaguesUseCase, getTeamsByLeagueUseCase, testDispatcher)

            val newQuery = "League"

            // WHEN: L'action de changement de query est envoyée
            viewModel.handler(TeamListAction.QueryChanged(newQuery))

            // THEN: L'état doit contenir la nouvelle query et les suggestions filtrées
            val expectedState =
                TeamListUIState(
                    query = newQuery,
                    suggestions = listOf("English Premier League").toImmutableList(),
                    leagues = listOf("French Ligue 1", "English Premier League").toImmutableList(),
                    status = ScreenStatus.IDLE, // Le statut ne change pas pour une simple query
                )
            assertEquals(expectedState, viewModel.uiState.value)
        }

    @Test
    fun `handler(QueryChanged) - after league selected - clears teams and updates query`() =
        runTest {
            // GIVEN: Le ViewModel est initialisé avec des ligues et un état de succès (équipes affichées)
            val leagues = listOf(League("1", "French Ligue 1"), League("2", "English Premier League"))
            coEvery { getAllLeaguesUseCase() } returns Result.success(leagues)
            coEvery { getTeamsByLeagueUseCase("French Ligue 1") } returns Result.success(
                listOf(Team("1", "PSG", "badge_url")),
            )
            viewModel = TeamListViewModel(getAllLeaguesUseCase, getTeamsByLeagueUseCase, testDispatcher)

            // Simule la sélection d'une ligue pour mettre l'état en SUCCESS avec des équipes
            viewModel.handler(TeamListAction.LeagueSelected("French Ligue 1"))
            assertEquals(ScreenStatus.SUCCESS, viewModel.uiState.value.status)
            assertEquals(1, viewModel.uiState.value.teams.size)

            // WHEN: L'action de changement de query est envoyée
            val newQuery = "Eng"
            viewModel.handler(TeamListAction.QueryChanged(newQuery))

            // THEN: L'état doit refléter la nouvelle query, les suggestions filtrées, et la liste des équipes doit être vide
            val expectedState =
                TeamListUIState(
                    query = newQuery,
                    suggestions = listOf("English Premier League").toImmutableList(),
                    teams = persistentListOf(),
                    leagues = listOf("French Ligue 1", "English Premier League").toImmutableList(),
                    status = ScreenStatus.IDLE,
                )
            assertEquals(expectedState, viewModel.uiState.value)
        }
}

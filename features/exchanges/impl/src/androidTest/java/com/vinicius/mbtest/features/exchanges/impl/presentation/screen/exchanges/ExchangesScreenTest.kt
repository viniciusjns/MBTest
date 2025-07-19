package com.vinicius.mbtest.features.exchanges.impl.presentation.screen.exchanges

import ExchangesViewIntent
import ExchangesViewModel
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.vinicius.mbtest.core.screen.ERROR_TEST_TAG
import com.vinicius.mbtest.core.screen.LOADING_TEST_TAG
import com.vinicius.mbtest.features.exchanges.domain.repository.ExchangesRepository
import com.vinicius.mbtest.features.exchanges.impl.di.exchangeModule
import com.vinicius.mbtest.features.exchanges.impl.navigation.ScreenRoutes
import com.vinicius.mbtest.features.exchanges.impl.navigation.SetupNavigation
import com.vinicius.mbtest.features.exchanges.impl.presentation.action.ExchangesAction
import com.vinicius.mbtest.features.exchanges.impl.presentation.screen.exchangeDetail.ExchangeDetailScreen
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangesSyncState
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangesViewState
import com.vinicius.mbtest.features.exchanges.impl.presentation.viewModel.ExchangeDetailViewModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class ExchangesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val initialState = ExchangesViewState()
    private val mockState = MutableStateFlow(initialState)
    private val mockAction = MutableSharedFlow<ExchangesAction>()
    private val mockViewModel = mockk<ExchangesViewModel> {
        every { state } returns mockState
        every { action } returns mockAction
    }

    @Test
    fun should_loadingState_displays_LoadingScreen() {
        every {
            mockViewModel.dispatchViewIntent(ExchangesViewIntent.FetchExchanges)
        } coAnswers {
            mockState.update {
                it.copy(syncState = ExchangesSyncState.Loading)
            }
        }

        composeTestRule.setContent {
            val navController = rememberNavController()
            ExchangeScreen(viewModel = mockViewModel, navController = navController)
        }

        composeTestRule
            .onNodeWithTag(LOADING_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun should_errorState_displays_ErrorScreen() {
        every {
            mockViewModel.dispatchViewIntent(ExchangesViewIntent.FetchExchanges)
        } coAnswers {
            mockState.update {
                it.copy(syncState = ExchangesSyncState.Error("An error occurred"))
            }
        }

        composeTestRule.setContent {
            val navController = rememberNavController()
            ExchangeScreen(viewModel = mockViewModel, navController = navController)
        }

        composeTestRule
            .onNodeWithTag(ERROR_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun successState_should_displays_ExchangeContentScreen() {
        every {
            mockViewModel.dispatchViewIntent(ExchangesViewIntent.FetchExchanges)
        } coAnswers {
            mockState.update {
                it.copy(
                    exchanges = exchangesStub(),
                    syncState = ExchangesSyncState.Success
                )
            }
        }

        composeTestRule.setContent {
            val navController = rememberNavController()
            ExchangeScreen(viewModel = mockViewModel, navController = navController)
        }

        // Aqui você precisa saber o que o ExchangeContentScreen exibe
        composeTestRule.onNodeWithTag(LIST_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun should_navigate_to_exchangeDetails_when_exchangeItem_is_clicked() {
        val exchanges = exchangesStub()
        val exchangeId = exchanges.first().exchangeId!!
        mockState.update { it.copy(exchanges = exchanges, syncState = ExchangesSyncState.Success) }

        every {
            mockViewModel.dispatchViewIntent(ExchangesViewIntent.FetchExchanges)
        } just Runs

        every {
            mockViewModel.dispatchViewIntent(ExchangesViewIntent.OnExchangeClicked(exchangeId))
        } coAnswers {
            mockAction.emit(ExchangesAction.NavigateToDetails(exchangeId))
        }

        composeTestRule.setContent {
            val navController = rememberNavController()
            SetupNavigation(navController, startDestination = ScreenRoutes.ExchangeScreenRoute.route)
            ExchangeScreen(navController = navController, viewModel = mockViewModel)
        }

        composeTestRule
            .onNodeWithTag("${EXCHANGES_ITEM_TEST_TAG}_${exchangeId}")
            .performClick()
    }

    companion object {
        private val mockRepo: ExchangesRepository = mockk()
        private val mockViewModel: ExchangeDetailViewModel = mockk(relaxed = true)
        private val module = module {
            single { mockRepo }
            viewModel { mockViewModel }
        }

        @JvmStatic
        @BeforeClass
        fun setUpClass() {
            every { mockRepo.getExchanges() } returns MutableStateFlow(emptyList())
            startKoin {
                androidContext(ApplicationProvider.getApplicationContext())
                modules(exchangeModule + module)
            }
        }

        @JvmStatic
        @AfterClass
        fun tearDownClass() {
            stopKoin()
        }
    }
}
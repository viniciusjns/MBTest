package com.vinicius.mbtest.features.exchanges.impl.presentation.screen.exchanges

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.vinicius.mbtest.features.exchanges.domain.repository.ExchangesRepository
import com.vinicius.mbtest.features.exchanges.impl.di.exchangeModule
import com.vinicius.mbtest.features.exchanges.impl.presentation.action.ExchangeDetailAction
import com.vinicius.mbtest.features.exchanges.impl.presentation.screen.exchangeDetail.ExchangeDetailScreen
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangeDetailSyncState
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangeDetailViewState
import com.vinicius.mbtest.features.exchanges.impl.presentation.viewModel.ExchangeDetailViewIntent
import com.vinicius.mbtest.features.exchanges.impl.presentation.viewModel.ExchangeDetailViewModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class ExchangeDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val initialState = ExchangeDetailViewState()
    private val mockState = MutableStateFlow(initialState)
    private val mockAction = MutableSharedFlow<ExchangeDetailAction>()
    private val mockViewModel = mockk<ExchangeDetailViewModel>(relaxed = true) {
        every { state } returns mockState
        every { action } returns mockAction
    }

    @Test
    fun when_DetailScreen_is_loaded_Then_should_display_elements_correctly() {
        val exchange = exchangeStub()
        mockState.update { it.copy(exchange = exchange, syncState = ExchangeDetailSyncState.Success) }
        every {
            mockViewModel.dispatchViewIntent(ExchangeDetailViewIntent.GetExchangeById(exchange.exchangeId!!))
        } just Runs

        composeTestRule.setContent {
            ExchangeDetailScreen(
                exchangeId = exchange.exchangeId!!,
                navController = rememberNavController()
            )
        }

        composeTestRule.onNodeWithText(exchange.name!!).assertExists()
        composeTestRule.onNodeWithText(exchange.website!!).assertExists()
    }

    @Test
    fun when_BackButton_is_clicked_then_calls_OnBackPressed_intent() {
        val exchange = exchangeStub()
        mockState.update { it.copy(exchange = exchange, syncState = ExchangeDetailSyncState.Success) }
        every {
            mockViewModel.dispatchViewIntent(ExchangeDetailViewIntent.OnBackPressed)
        } just Runs

        composeTestRule.setContent {
            ExchangeDetailScreen(
                exchangeId = exchange.exchangeId!!,
                navController = rememberNavController()
            )
        }

        composeTestRule
            .onNodeWithTag("BACK_TEST_TAG")
            .performClick()

        verify(exactly = 1) {
            mockViewModel.dispatchViewIntent(ExchangeDetailViewIntent.OnBackPressed)
        }
    }

    @Before
    fun setup() {
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(module {
                viewModel { mockViewModel }
            })
        }
    }

    @After
    fun tearDownClass() {
        stopKoin()
    }
}
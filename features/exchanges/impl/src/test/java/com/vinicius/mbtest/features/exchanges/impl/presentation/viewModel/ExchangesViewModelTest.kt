package com.vinicius.mbtest.features.exchanges.impl.presentation.viewModel

import ExchangesViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.vinicius.mbtest.core.test.MainCoroutineTestRule
import com.vinicius.mbtest.features.exchanges.domain.useCase.GetExchangesUseCase
import com.vinicius.mbtest.features.exchanges.impl.presentation.action.ExchangesAction
import com.vinicius.mbtest.features.exchanges.impl.presentation.mapper.toDataUi
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangesSyncState
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangesViewState
import com.vinicius.mbtest.features.exchanges.impl.stub.exchangesStub
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ExchangesViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getExchangesUseCase: GetExchangesUseCase = mockk(relaxed = true)
    private val viewModel = ExchangesViewModel(
        getExchangesUseCase = getExchangesUseCase
    )

    @Test
    fun `should emit loading and then success with exchange list`() = runTest {
        // Given
        val exchanges = exchangesStub()
        val expectedState = ExchangesViewState(
            exchanges = exchanges.map { it.toDataUi() },
            syncState = ExchangesSyncState.Success
        )
        coEvery { getExchangesUseCase() } returns flow { emit(exchanges) }

        // When
        viewModel.dispatchViewIntent(ExchangesViewIntent.FetchExchanges)

        // Then
        viewModel.state.test {
            assertEquals(ExchangesViewState(syncState = ExchangesSyncState.Loading), awaitItem())
            assertEquals(expectedState, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should emit loading and then error when use case throws`() = runTest {
        // Given
        coEvery { getExchangesUseCase() } returns flow {
            throw RuntimeException("Network error")
        }

        // When
        viewModel.dispatchViewIntent(ExchangesViewIntent.FetchExchanges)

        // Then
        viewModel.state.test {
            assertEquals(ExchangesViewState(syncState = ExchangesSyncState.Loading), awaitItem())
            assertEquals(ExchangesViewState(syncState = ExchangesSyncState.Error("Network error")), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should navigate to details when exchange clicked`() = runTest {
        // Given
        val exchangeId = "12345"
        val expectedAction = ExchangesAction.NavigateToDetails(exchangeId)

        // When
        viewModel.dispatchViewIntent(ExchangesViewIntent.OnExchangeClicked(exchangeId))

        // Then
        viewModel.action.test {
            assertEquals(expectedAction, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}

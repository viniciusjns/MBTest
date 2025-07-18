package com.vinicius.mbtest.features.exchanges.impl.presentation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.vinicius.mbtest.core.test.MainCoroutineTestRule
import com.vinicius.mbtest.features.exchanges.domain.useCase.GetExchangeByIdUseCase
import com.vinicius.mbtest.features.exchanges.impl.presentation.action.ExchangeDetailAction
import com.vinicius.mbtest.features.exchanges.impl.presentation.mapper.toDataUi
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangeDetailSyncState
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangeDetailViewState
import com.vinicius.mbtest.features.exchanges.impl.stub.exchangeStub
import io.mockk.mockk
import io.mockk.coEvery
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ExchangeDetailViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getExchangeByIdUseCase: GetExchangeByIdUseCase = mockk()
    private val viewModel = ExchangeDetailViewModel(
        getExchangeByIdUseCase = getExchangeByIdUseCase
    )

    @Test
    fun `should return selected exchange`() = runTest {
        // Given
        val selectedExchange = exchangeStub()
        val exchangeId = selectedExchange.exchangeId
        coEvery { getExchangeByIdUseCase(exchangeId!!) } returns selectedExchange

        // When
        viewModel.dispatchViewIntent(ExchangeDetailViewIntent.GetExchangeById(exchangeId!!))

        // Then
        viewModel.state.test {
            assertEquals(
                ExchangeDetailViewState(
                    exchange = selectedExchange.toDataUi(),
                    syncState = ExchangeDetailSyncState.Success
                ),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should call OnBackPressed`() = runTest {
        // Given

        // When
        viewModel.dispatchViewIntent(ExchangeDetailViewIntent.OnBackPressed)

        // Then
        viewModel.action.test {
            assertEquals(ExchangeDetailAction.OnBackPressed, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
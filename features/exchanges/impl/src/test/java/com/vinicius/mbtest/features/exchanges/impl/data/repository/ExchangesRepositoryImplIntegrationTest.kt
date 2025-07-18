package com.vinicius.mbtest.features.exchanges.impl.data.repository

import app.cash.turbine.test
import com.vinicius.mbtest.core.extensions.readJsonFile
import com.vinicius.mbtest.core.test.MainCoroutineTestRule
import com.vinicius.mbtest.core.test.RemoteTestRule
import com.vinicius.mbtest.features.exchanges.domain.repository.ExchangesRepository
import com.vinicius.mbtest.features.exchanges.impl.data.local.datasource.ExchangesLocalDataSourceImpl
import com.vinicius.mbtest.features.exchanges.impl.data.remote.datasource.ExchangesRemoteDataSourceImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.FileNotFoundException

private const val EXCHANGE_SUCCESS_RESPONSE = "coin_api_exchanges_success_response.json"
private const val EXCHANGE_ERROR_RESPONSE = "coin_api_exchanges_error_response.json"

class ExchangesRepositoryImplIntegrationTest {

    @get:Rule
    val remoteTestRule = RemoteTestRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineTestRule()

    private lateinit var repository: ExchangesRepository

    @Before
    fun setup() {
        repository = ExchangesRepositoryImpl(
            remoteDataSource = ExchangesRemoteDataSourceImpl(
                service = remoteTestRule.createTestService(),
                dispatcher = mainCoroutineRule.testDispatcher
            ),
            localDataSource = ExchangesLocalDataSourceImpl()
        )
    }

    @Test
    fun `getExchanges should return success`() = runTest {
        // Given
        val expectedSize = 2
        val expectedExchangeId = "MERCADOBITCOIN"
        remoteTestRule.enqueueResponse(EXCHANGE_SUCCESS_RESPONSE.readJsonFile(), code = 200)

        // When
        val result = repository.getExchanges()

        // Then
        result.test {
            val exchanges = awaitItem()
            assertEquals(expectedSize, exchanges.size)
            assertEquals(expectedExchangeId, exchanges.first().exchangeId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getExchanges should return error`() = runTest {
        // Given
        remoteTestRule.enqueueResponse(EXCHANGE_ERROR_RESPONSE.readJsonFile(), code = 500)

        // When
        val result = repository.getExchanges()

        // Then
        result.test {
            val error = awaitError()
            assertTrue(error is Exception)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
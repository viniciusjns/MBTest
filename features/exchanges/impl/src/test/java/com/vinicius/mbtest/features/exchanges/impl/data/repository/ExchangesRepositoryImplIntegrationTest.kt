package com.vinicius.mbtest.features.exchanges.impl.data.repository

import app.cash.turbine.test
import com.vinicius.mbtest.core.extensions.readJsonFile
import com.vinicius.mbtest.core.test.MainCoroutineTestRule
import com.vinicius.mbtest.core.test.RemoteTestRule
import com.vinicius.mbtest.features.exchanges.domain.repository.ExchangesRepository
import com.vinicius.mbtest.features.exchanges.impl.data.local.dao.ExchangeDAO
import com.vinicius.mbtest.features.exchanges.impl.data.local.datasource.ExchangesLocalDataSourceImpl
import com.vinicius.mbtest.features.exchanges.impl.data.mapper.toDomain
import com.vinicius.mbtest.features.exchanges.impl.data.remote.datasource.ExchangesRemoteDataSourceImpl
import com.vinicius.mbtest.features.exchanges.impl.stub.exchangesEntityStub
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val EXCHANGE_SUCCESS_RESPONSE = "coin_api_exchanges_success_response.json"
private const val EXCHANGE_ERROR_RESPONSE = "coin_api_exchanges_error_response.json"
private const val ICONS_SUCCESS_RESPONSE = "coin_api_icons_success_response.json"

class ExchangesRepositoryImplIntegrationTest {

    @get:Rule
    val remoteTestRule = RemoteTestRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineTestRule()

    private val exchangeDao: ExchangeDAO = mockk(relaxed = true)
    private val localDataSource = ExchangesLocalDataSourceImpl(exchangeDAO = exchangeDao)

    private lateinit var repository: ExchangesRepository

    @Before
    fun setup() {
        repository = ExchangesRepositoryImpl(
            remoteDataSource = ExchangesRemoteDataSourceImpl(
                service = remoteTestRule.createTestService(),
                dispatcher = mainCoroutineRule.testDispatcher
            ),
            localDataSource = localDataSource
        )
    }

    @Test
    fun `getExchanges should return success and store data locally`() = runTest {
        // Given
        val expectedSize = 2
        val expectedExchangeId = "exch_1"

        remoteTestRule.enqueueResponse(EXCHANGE_SUCCESS_RESPONSE.readJsonFile(), code = 200)
        remoteTestRule.enqueueResponse(ICONS_SUCCESS_RESPONSE.readJsonFile(), code = 200)

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
    fun `getExchanges should return fallback when some error happens`() = runTest {
        // Given
        val exchangesEntity = exchangesEntityStub()
        val expectedFallback = exchangesEntity.map { it.toDomain() }
        remoteTestRule.enqueueResponse(EXCHANGE_ERROR_RESPONSE.readJsonFile(), code = 500)
        coEvery { localDataSource.getExchanges() } returns exchangesEntity

        // When
        val result = repository.getExchanges()

        // Then
        result.test {
            assertEquals(expectedFallback, awaitItem())
            awaitComplete()
        }
    }
}
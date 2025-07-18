package com.vinicius.mbtest.features.exchanges.impl.data.repository

import app.cash.turbine.test
import com.vinicius.mbtest.core.test.MainCoroutineTestRule
import com.vinicius.mbtest.core.test.RemoteTestRule
import com.vinicius.mbtest.features.exchanges.impl.data.local.datasource.ExchangesLocalDataSourceImpl
import com.vinicius.mbtest.features.exchanges.impl.data.remote.datasource.ExchangesRemoteDataSourceImpl
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.FileNotFoundException

private const val SUCCESS_EXCHANGE_RESPONSE = "coin_api_exchanges_success_response.json"

class ExchangesRepositoryImplIntegrationTest {

    @get:Rule
    val remoteTestRule = RemoteTestRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineTestRule()

    private lateinit var repository: ExchangesRepositoryImpl

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
    fun `When getExchanges is invoked Then should return success response`() {
        // Given
        val expectedSize = 2
        val expectedExchangeId = "MERCADOBITCOIN"
        remoteTestRule.enqueueResponse(SUCCESS_EXCHANGE_RESPONSE.readJsonFile(), code = 200)

        // When
        val result = repository.getExchanges()

        // Then
        runTest {
            result.test {
                assertEquals(expectedSize, awaitItem().size)
                assertEquals(expectedExchangeId, awaitItem().first().exchangeId)
                awaitComplete()
            }
        }
    }
}

fun String.readJsonFile(): String =
    requireNotNull(ClassLoader.getSystemResource(this)?.readText()) {
        throw FileNotFoundException("File $this not found!")
    }
package com.vinicius.mbtest.features.exchanges.impl.domain

import app.cash.turbine.test
import com.vinicius.mbtest.features.exchanges.domain.repository.ExchangesRepository
import com.vinicius.mbtest.features.exchanges.impl.domain.useCase.GetExchangesUseCaseImpl
import com.vinicius.mbtest.features.exchanges.impl.stub.exchangesStub
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetExchangesUseCaseImplTest {

    private val repository: ExchangesRepository = mockk()
    private val getExchangesUseCase = GetExchangesUseCaseImpl(
        repository = repository
    )

    @Test
    fun `invoke should return list of exchanges from repository`() = runBlocking {
        val expected = exchangesStub()
        coEvery { repository.getExchanges() } returns flow { emit(expected) }

        val result = getExchangesUseCase.invoke()

        result.test {
            assertEquals(expected, awaitItem())
            awaitComplete()
        }
        verify(exactly = 1) { repository.getExchanges() }
    }
}
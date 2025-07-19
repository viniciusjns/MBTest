package com.vinicius.mbtest.features.exchanges.impl.domain

import com.vinicius.mbtest.features.exchanges.domain.repository.ExchangesRepository
import com.vinicius.mbtest.features.exchanges.impl.domain.useCase.GetExchangeByIdUseCaseImpl
import com.vinicius.mbtest.features.exchanges.impl.stub.exchangeStub
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetExchangeByIdUseCaseImplTest {

    private val repository: ExchangesRepository = mockk(relaxed = true)
    private val getExchangesUseCase = GetExchangeByIdUseCaseImpl(
        repository = repository
    )

    @Test
    fun `invoke should return exchange by id from repository`() = runBlocking {
        val expected = exchangeStub()
        val mockId = "1"
        coEvery { repository.getExchangeById(mockId) } returns expected

        val result = getExchangesUseCase.invoke(mockId)

        assertEquals(expected, result)
        coVerify(exactly = 1) { repository.getExchangeById(mockId) }
    }
}
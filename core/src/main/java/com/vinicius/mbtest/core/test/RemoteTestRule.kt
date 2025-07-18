package com.vinicius.mbtest.core.test

import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteTestRule : TestWatcher() {

    private val mockWebServer = MockWebServer()

    override fun starting(description: Description) {
        super.starting(description)
        mockWebServer.start()
    }

    override fun finished(description: Description) {
        super.finished(description)
        mockWebServer.shutdown()
    }

    fun enqueueResponse(body: String, code: Int) {
        mockWebServer.enqueue(MockResponse().setBody(body).setResponseCode(code))
    }

    fun simulateException(exception: Exception) {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                throw exception
            }
        }
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun <reified Service> createTestService(): Service =
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(Service::class.java)
}
package com.vinicius.mbtest.di

import com.vinicius.mbtest.BuildConfig
import com.vinicius.mbtest.interceptor.ApiKeyInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        provideHttpClient(
            interceptors = listOf(
                ApiKeyInterceptor(BuildConfig.COIN_API_KEY),
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY },
            )
        )
    }
    single { provideConverterFactory() }
    single { provideRetrofit(okHttpClient = get(), gsonConverterFactory = get()) }
}

private fun provideHttpClient(
    interceptors: List<Interceptor>
): OkHttpClient =
    OkHttpClient
        .Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .apply { interceptors.forEach { addInterceptor(it) } }
        .build()

private fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

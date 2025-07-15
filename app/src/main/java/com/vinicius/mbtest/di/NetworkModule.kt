package com.vinicius.mbtest.di

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { provideRetrofit(okHttpClient = get(), gsonConverterFactory = get()) }
}

private fun provideHttpClient(): OkHttpClient =
    OkHttpClient
        .Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

private fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit =
    Retrofit.Builder()
        .baseUrl("http://rest.coinapi.io/")
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

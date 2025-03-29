package dev.hirogakatageri.boring.core

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.hirogakatageri.boring.user.remote.UserApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Koin module for network-related dependencies.
 */
val networkModule = module {
    // Base URLs for the APIs
    val dummyJsonBaseUrl = "https://dummyjson.com/"

    // Provide OkHttpClient
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Provide Moshi
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    // Provide Retrofit for DummyJSON
    single {
        Retrofit.Builder()
            .baseUrl(dummyJsonBaseUrl)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    // Provide UserApiService
    single {
        get<Retrofit>().create(UserApiService::class.java)
    }
}
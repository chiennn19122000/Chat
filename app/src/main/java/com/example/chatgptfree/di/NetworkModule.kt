package com.example.chatgptfree.di

import com.example.chatgptfree.utils.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    fun initHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
        builder.addInterceptor {
            val original = it.request()
            val url = original.url()
                .newBuilder()
                .build()
            val request = original.newBuilder().url(url).build()
            it.proceed(request)
        }
        return builder.build()
    }

    fun initRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()

    single { initHttpClient() }
    single { initRetrofit(get()) }
}

package com.app.pagedlibraryapplication.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceBuilder {

    // Base URL
    private const val URL = "https://skwibble.poplify.com/"

    // Create Logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)




    // Create OkHttp Client
    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(object :Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                val request: Request =
                    chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json;version=3")
                        .build()
                return chain.proceed(request)
            }

        })
        .addInterceptor(logger)



    // Create Retrofit Builder
    private val builder = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())


    // Create Retrofit Instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}
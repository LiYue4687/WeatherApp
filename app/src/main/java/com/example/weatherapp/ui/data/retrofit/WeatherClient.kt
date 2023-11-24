package com.example.weatherapp.ui.data.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object WeatherClient {
    const val baseUrl = "https://restapi.amap.com/v3/weather/"

    val okHttpClient= OkHttpClient.Builder().callTimeout(30, TimeUnit.SECONDS ).build()

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val weatherAPI = retrofit.create(WeatherAPI::class.java)
}
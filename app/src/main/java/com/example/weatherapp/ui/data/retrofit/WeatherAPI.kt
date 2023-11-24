package com.example.weatherapp.ui.data.retrofit

import com.example.weatherapp.ui.data.retrofit.entity.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

public interface WeatherAPI {

    //    @GET("weatherInfo?key={key}&city={city}&extensions={extensions}")
    @GET("weatherInfo")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("city") city: String,
        @Query("extensions") extensions: String
    ): WeatherResponse
}
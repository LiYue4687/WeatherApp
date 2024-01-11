package com.example.weatherapp.data.retrofit

import com.example.weatherapp.data.retrofit.entity.CitySearchResponse
import com.example.weatherapp.data.retrofit.entity.Weather24hourResponse
import com.example.weatherapp.data.retrofit.entity.WeatherResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

public interface WeatherAPI {

    //    @GET("weatherInfo?key={key}&city={city}&extensions={extensions}")
    @GET("GetWeatherForecast")
    suspend fun getWeather(
        @Query("city") city: String,
        @Query("extensions") extensions: String
    ): WeatherResponse

    @GET("GetSearchCity")
    suspend fun getSearchCity(
        @Query("searchValue") searchValue: String
    ): CitySearchResponse

    @FormUrlEncoded
    @POST("GetWeather24hour")
    suspend fun getWeather24hour(
        @Field("city") city: String,
        @Field("extensions") extensions: String
    ): Weather24hourResponse
}
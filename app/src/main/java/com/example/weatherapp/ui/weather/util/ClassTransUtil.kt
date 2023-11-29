package com.example.weatherapp.ui.weather.util

import com.example.weatherapp.data.retrofit.entity.CastItem
import com.example.weatherapp.data.retrofit.entity.Forecast
import com.example.weatherapp.data.retrofit.entity.WeatherResponse
import com.example.weatherapp.data.room.entity.ForecastEntity
import com.example.weatherapp.ui.weather.WeatherState

object ClassTransUtil {
    fun translateWeatherResponseToState(weatherResponse: WeatherResponse): WeatherState {
        val forecast = weatherResponse.forecasts[0]
        val name = forecast.province + "  " + forecast.city
        var weatherByHour: MutableList<Pair<String, Int>> = mutableListOf()
        var weatherByDay: MutableList<Triple<String, Int, Int>> = mutableListOf()
        for (i in 0..23) {
            weatherByHour.add(Pair("sun", 10 + i))
        }
        for (i in forecast.casts.indices) {
            weatherByDay.add (Triple(
                forecast.casts[i].dayweather,
                forecast.casts[i].daytemp.toInt(),
                forecast.casts[i].nighttemp.toInt()
            ))
        }
        return WeatherState(
            name, 10, 0.6f, 0.6f, Pair("6:00", "18:00"),
            weatherByHour.toList(), weatherByDay.toList()
        )
    }

    fun translateSqlResultToSate(name:String, forecasts:List<ForecastEntity>): WeatherState {
        var weatherByHour: MutableList<Pair<String, Int>> = mutableListOf()
        var weatherByDay: MutableList<Triple<String, Int, Int>> = mutableListOf()
        for (i in 0..23) {
            weatherByHour.add(Pair("sun", 10 + i))
        }
        for (i in forecasts.indices) {
            weatherByDay.add(
                Triple(
                    forecasts[i].dayweather,
                    forecasts[i].daytemp.toInt(),
                    forecasts[i].nighttemp.toInt()
                ))
        }
        return WeatherState(
            name, 10, 0.6f, 0.6f, Pair("6:00", "18:00"),
            weatherByHour.toList(), weatherByDay.toList()
        )
    }

    fun buildWeatherForecast(
        count: Int,
        forecast: Forecast,
        cast: CastItem
    ): ForecastEntity {
        return ForecastEntity(
            forecast.province + "_" + forecast.city + "_" + count,
            forecast.adcode,
            cast.date,
            cast.dayweather,
            cast.nightweather,
            cast.daytemp,
            cast.nighttemp,
            cast.daywind,
            cast.nightwind,
            cast.daypower,
            cast.nightpower
        )
    }
}
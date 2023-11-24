package com.example.weatherapp.ui.data.retrofit.entity

data class WeatherResponse(
    val status: String,
    val forecasts: List<Forecast>
)

class Forecast(
    val city:String,
    val adcode: String,
    val province:String,
    val reporttime:String,
    val casts:List<CastItem>
)

class CastItem(
    val date:String,
    val week: String,
    val dayweather:String,
    val nightweather: String,
    val daytemp:String,
    val nighttemp:String,
    val daywind: String,
    val nightwind: String,
    val daypower: String,
    val nightpower: String
)
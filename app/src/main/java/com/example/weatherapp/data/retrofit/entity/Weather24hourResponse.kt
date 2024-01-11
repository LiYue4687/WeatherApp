package com.example.weatherapp.data.retrofit.entity
data class Weather24hourResponse(
    val status: String,
    val cityInfo: CityInfo,
    val forecast24hour: List<Forecast24hourItem>
)

class CityInfo(
    val city:String,
    val adCode: String,
    val province:String,
    val reportTime:String,
)

class Forecast24hourItem(
    val time:String,
    val temp: String,
    val weather: String,
)
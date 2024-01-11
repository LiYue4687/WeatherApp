package com.example.weatherapp.data.retrofit.entity

data class CitySearchResponse (
    val status: String,
    val cities: List<CityItem>
)

class CityItem  (
    val provinceName: String,
    val cityName: String,
    val countyName: String,
    val cityCode: String,
    val adCode: String,
)

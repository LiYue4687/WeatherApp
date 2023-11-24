package com.example.weatherapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey val uid: String = "unknown",
    val code: String,
    val date: String,
    val dayweather: String,
    val nightweather: String,
    val daytemp: String,
    val nighttemp: String,
    val daywind: String,
    val nightwind: String,
    val daypower: String,
    val nightpower: String

)

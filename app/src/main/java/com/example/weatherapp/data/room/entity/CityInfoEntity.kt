package com.example.weatherapp.data.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "city_info")
class CityInfoEntity(
    val province:String,
    val city:String,
    @PrimaryKey val city_code: String = ""
) {
}